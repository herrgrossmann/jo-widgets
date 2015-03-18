/*
 * Copyright (c) 2011, Michael Grossmann, Nikolaus Moll
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.impl.widgets.composed;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.api.convert.IConverter;
import org.jowidgets.api.convert.IObjectStringConverter;
import org.jowidgets.api.layout.ILayoutFactory;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IButton;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IInputControl;
import org.jowidgets.api.widgets.IInputDialog;
import org.jowidgets.api.widgets.IInputField;
import org.jowidgets.api.widgets.ITextControl;
import org.jowidgets.api.widgets.blueprint.IButtonBluePrint;
import org.jowidgets.api.widgets.blueprint.ICollectionInputDialogBluePrint;
import org.jowidgets.api.widgets.descriptor.ICollectionInputFieldDescriptor;
import org.jowidgets.api.widgets.descriptor.setup.ICollectionInputDialogSetup;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.types.Modifier;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.types.Rectangle;
import org.jowidgets.common.types.VirtualKey;
import org.jowidgets.common.widgets.controller.IActionListener;
import org.jowidgets.common.widgets.controller.IInputListener;
import org.jowidgets.common.widgets.controller.IKeyEvent;
import org.jowidgets.common.widgets.controller.IKeyListener;
import org.jowidgets.common.widgets.controller.IMouseListener;
import org.jowidgets.common.widgets.layout.ILayouter;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.i18n.api.IMessage;
import org.jowidgets.i18n.api.MessageReplacer;
import org.jowidgets.tools.controller.InputObservable;
import org.jowidgets.tools.controller.KeyAdapter;
import org.jowidgets.tools.validation.ValidationCache;
import org.jowidgets.tools.validation.ValidationCache.IValidationResultCreator;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.tools.widgets.wrapper.ControlWrapper;
import org.jowidgets.util.Assert;
import org.jowidgets.util.EmptyCheck;
import org.jowidgets.validation.IValidationConditionListener;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.IValidationResultBuilder;
import org.jowidgets.validation.IValidator;
import org.jowidgets.validation.ValidationResult;
import org.jowidgets.validation.tools.CompoundValidator;

public class CollectionInputFieldImpl<ELEMENT_TYPE> extends ControlWrapper implements
        IInputControl<Collection<ELEMENT_TYPE>>,
        ITextControl {

    private static final IMessage ELEMENT = Messages.getMessage("CollectionInputFieldImpl.element"); //$NON-NLS-1$
    private static final IMessage EDIT = Messages.getMessage("CollectionInputFieldImpl.edit"); //$NON-NLS-1$
    private static final Character DEFAULT_SEPARATOR = Character.valueOf(',');

    private final boolean filterEmptyValues;
    private final boolean dublicatesAllowed;
    private final IInputField<String> textField;
    private final IButton editButton;
    private final IComposite editButtonContainer;
    private final IConverter<ELEMENT_TYPE> converter;
    private final IObjectStringConverter<ELEMENT_TYPE> objectStringConverter;
    private final Character separator;
    private final Character maskingCharacter;
    private final ValidationCache validationCache;
    private final CompoundValidator<Collection<ELEMENT_TYPE>> compoundValidator;
    private final InputObservable inputObservable;
    private final ICollectionInputDialogBluePrint<ELEMENT_TYPE> inputDialogBp;

    private Dimension lastDialogSize;

    private final Collection<String> value;
    private final Collection<ELEMENT_TYPE> elementTypeValue;

    private boolean editable; // TODO MG replace by getter when implemented

    @SuppressWarnings("unchecked")
    public CollectionInputFieldImpl(final IComposite composite, final ICollectionInputFieldDescriptor<ELEMENT_TYPE> setup) {
        super(composite);

        this.filterEmptyValues = setup.isFilterEmptyValues();
        this.dublicatesAllowed = setup.getDublicatesAllowed();

        Assert.paramNotNull(setup.getConverter(), "setup.getConverter()");
        if (setup.getConverter() instanceof IConverter<?>) {
            this.converter = (IConverter<ELEMENT_TYPE>) setup.getConverter();
            this.objectStringConverter = converter;
        }
        else if (setup.getConverter() instanceof IObjectStringConverter<?>) {
            this.converter = null;
            this.objectStringConverter = (IObjectStringConverter<ELEMENT_TYPE>) setup.getConverter();
        }
        else {
            throw new IllegalArgumentException("Converter type'" + setup.getConverter().getClass() + "' is not supported.");
        }

        if (setup.getSeparator() != null) {
            this.separator = setup.getSeparator();
        }
        else {
            this.separator = DEFAULT_SEPARATOR;
        }
        this.maskingCharacter = setup.getMaskingCharacter();

        final ICollectionInputDialogSetup<ELEMENT_TYPE> inputDialogSetup = setup.getCollectionInputDialogSetup();

        this.value = new LinkedList<String>();
        this.elementTypeValue = new LinkedList<ELEMENT_TYPE>();
        this.inputObservable = new InputObservable();
        this.compoundValidator = new CompoundValidator<Collection<ELEMENT_TYPE>>();

        this.textField = composite.add(BPF.inputFieldString(), "grow, w 0::, sgy hg");
        textField.addInputListener(new IInputListener() {
            @Override
            public void inputChanged() {
                inputChangedListener();
            }
        });

        final IValidator<ELEMENT_TYPE> elementValidator = setup.getElementValidator();
        this.validationCache = new ValidationCache(new IValidationResultCreator() {
            @Override
            public IValidationResult createValidationResult() {
                final IValidationResultBuilder builder = ValidationResult.builder();
                int index = 1;
                for (final String element : value) {
                    boolean invalid = false;
                    final String context = MessageReplacer.replace(ELEMENT.get(), String.valueOf(index));

                    if (converter != null && converter.getStringValidator() != null) {
                        final IValidator<String> stringValidator = converter.getStringValidator();
                        final IValidationResult stringResult = stringValidator.validate(element).withContext(context);
                        if (!stringResult.isValid()) {
                            invalid = true;
                        }
                        builder.addResult(stringResult);
                    }
                    if (converter != null && !invalid) {
                        final ELEMENT_TYPE convertedElement = converter.convertToObject(element);
                        final IValidationResult elementResult = elementValidator.validate(convertedElement).withContext(context);
                        builder.addResult(elementResult);
                    }

                    index++;
                }
                final Collection<ELEMENT_TYPE> currentValue = getValue();
                builder.addResult(compoundValidator.validate(currentValue));
                if (!dublicatesAllowed
                    && currentValue != null
                    && currentValue.size() != new HashSet<ELEMENT_TYPE>(currentValue).size()) {
                    builder.addError(Messages.getString("CollectionInputFieldImpl.contains_dublicates"));
                }

                return builder.build();
            }
        });

        if (inputDialogSetup != null) {
            inputDialogBp = BPF.collectionInputDialog(inputDialogSetup.getCollectionInputControlSetup());
            inputDialogBp.setSetup(inputDialogSetup);
            inputDialogBp.setValidator(setup.getValidator());

            final IButtonBluePrint buttonBp = BPF.button();
            if (setup.getEditButtonIcon() != null) {
                buttonBp.setIcon(setup.getEditButtonIcon());

            }
            else {
                buttonBp.setText(EDIT.get());
            }

            editButtonContainer = composite.add(BPF.composite());
            this.editButton = editButtonContainer.add(buttonBp);
            editButtonContainer.setLayout(new EditButtonConatinerLayouterFactory());

            this.editButton.addActionListener(new IActionListener() {
                @Override
                public void actionPerformed() {
                    openDialog();
                }
            });

            textField.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(final IKeyEvent event) {
                    if (event.getModifier().contains(Modifier.ALT) && VirtualKey.ENTER.equals(event.getVirtualKey())) {
                        openDialog();
                    }
                }

            });

            composite.setLayout(new TextFieldWithButtonLayouterFactory());

        }
        else {
            composite.setLayout(new MigLayoutDescriptor("0[grow, 0::]0", "0[grow]0"));
            this.inputDialogBp = null;
            this.editButtonContainer = null;
            this.editButton = null;
        }

        if (setup.getValidator() != null) {
            compoundValidator.addValidator(setup.getValidator());
        }

        if (converter == null) {
            textField.setEditable(false);
        }

        this.editable = setup.isEditable();
        if (!setup.isEditable()) {
            setEditable(false);
        }
    }

    @SuppressWarnings("unchecked")
    private void openDialog() {
        final Position buttonPos = Toolkit.toScreen(editButton.getPosition(), getWidget());
        inputDialogBp.setPosition(buttonPos);
        if (lastDialogSize != null) {
            inputDialogBp.setSize(lastDialogSize);
        }
        else {
            inputDialogBp.setSize(new Dimension(300, 270));
        }
        final IInputDialog<Collection<ELEMENT_TYPE>> dialog = Toolkit.getActiveWindow().createChildWindow(inputDialogBp);

        if (EmptyCheck.isEmpty(value)) {
            dialog.setValue((Collection<ELEMENT_TYPE>) Collections.singleton(null));
        }
        else {
            dialog.setValue(getValue());
        }
        dialog.setVisible(true);

        if (dialog.isOkPressed()) {
            lastDialogSize = dialog.getSize();
            setValue(dialog.getValue());
        }

        dialog.dispose();
    }

    @Override
    protected IComposite getWidget() {
        return (IComposite) super.getWidget();
    }

    @Override
    public void addValidator(final IValidator<Collection<ELEMENT_TYPE>> validator) {
        compoundValidator.addValidator(validator);
    }

    @Override
    public boolean hasModifications() {
        return textField.hasModifications();
    }

    @Override
    public void resetModificationState() {
        textField.resetModificationState();
    }

    @Override
    public void setValue(final Collection<ELEMENT_TYPE> value) {
        this.value.clear();
        this.elementTypeValue.clear();
        if (EmptyCheck.isEmpty(value)) {
            textField.setValue(null);
        }
        else {
            this.elementTypeValue.addAll(value);
            final String maskingString = String.valueOf(maskingCharacter.charValue());
            final String separatorString = String.valueOf(separator.charValue());
            final StringBuilder valueString = new StringBuilder();
            for (final ELEMENT_TYPE element : value) {
                final String converted = objectStringConverter.convertToString(element);
                this.value.add(converted);

                if (converted != null) {
                    final String masked = converted.replace(maskingString, maskingString + maskingString);
                    if (converted.contains(separatorString) || converted.startsWith(" ")) {
                        valueString.append(maskingString + masked + maskingString);
                    }
                    else {
                        valueString.append(masked);
                    }
                }
                valueString.append(separator + " ");
            }
            valueString.replace(valueString.length() - 2, valueString.length(), "");
            textField.setValue(valueString.toString());
        }
    }

    @Override
    public Collection<ELEMENT_TYPE> getValue() {
        final List<ELEMENT_TYPE> result = new LinkedList<ELEMENT_TYPE>();
        if (converter != null) {
            for (final String element : value) {
                final ELEMENT_TYPE converted = converter.convertToObject(element);
                if (!filterEmptyValues || (!EmptyCheck.isEmpty(converted) && !EmptyCheck.isEmpty(element))) {
                    result.add(converted);
                }
            }
        }
        else {
            result.addAll(elementTypeValue);
        }
        return result;
    }

    @Override
    public IValidationResult validate() {
        return validationCache.validate();
    }

    @Override
    public void addValidationConditionListener(final IValidationConditionListener listener) {
        validationCache.addValidationConditionListener(listener);
    }

    @Override
    public void removeValidationConditionListener(final IValidationConditionListener listener) {
        validationCache.removeValidationConditionListener(listener);
    }

    @Override
    public void setEditable(final boolean editable) {
        this.editable = editable;
        if (converter != null) {
            textField.setEditable(editable);
        }
        if (editButton != null) {
            editButton.setEnabled(editable && textField.isEnabled());
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        textField.setEnabled(enabled);
        if (editButton != null) {
            editButton.setEnabled(enabled && editable);
        }
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public boolean requestFocus() {
        return textField.requestFocus();
    }

    @Override
    public void addKeyListener(final IKeyListener listener) {
        textField.addKeyListener(listener);
    }

    @Override
    public void removeKeyListener(final IKeyListener listener) {
        textField.removeKeyListener(listener);
    }

    @Override
    public void addMouseListener(final IMouseListener listener) {
        textField.addMouseListener(listener);
    }

    @Override
    public void removeMouseListener(final IMouseListener listener) {
        textField.removeMouseListener(listener);
    }

    @Override
    public void addInputListener(final IInputListener listener) {
        inputObservable.addInputListener(listener);
    }

    @Override
    public void removeInputListener(final IInputListener listener) {
        inputObservable.removeInputListener(listener);
    }

    @Override
    public void setForegroundColor(final IColorConstant colorValue) {
        textField.setForegroundColor(colorValue);
    }

    @Override
    public void setBackgroundColor(final IColorConstant colorValue) {
        textField.setBackgroundColor(colorValue);
    }

    @Override
    public IColorConstant getForegroundColor() {
        return textField.getForegroundColor();
    }

    @Override
    public IColorConstant getBackgroundColor() {
        return textField.getBackgroundColor();
    }

    @Override
    public String getText() {
        return textField.getText();
    }

    @Override
    public void setText(final String text) {
        textField.setValue(text);
    }

    @Override
    public void setFontSize(final int size) {}

    @Override
    public void setFontName(final String fontName) {}

    @Override
    public void setMarkup(final Markup markup) {}

    @Override
    public void setSelection(final int start, final int end) {
        textField.setSelection(start, end);
    }

    @Override
    public void setCaretPosition(final int pos) {
        textField.setCaretPosition(pos);
    }

    @Override
    public int getCaretPosition() {
        return textField.getCaretPosition();
    }

    @Override
    public void selectAll() {
        textField.selectAll();
    }

    @Override
    public void select() {
        selectAll();
    }

    private void inputChangedListener() {
        final String maskingString = String.valueOf(maskingCharacter.charValue());
        final String separatorString = String.valueOf(separator.charValue());

        value.clear();
        final String input = textField.getValue();

        boolean isQuoted = false;
        int pos = 0;
        final StringBuilder currentElement = new StringBuilder();
        while (pos < input.length()) {
            if (equalsStringPart(input, maskingString, pos)) {
                pos = pos + maskingString.length();
                if (equalsStringPart(input, maskingString, pos)) {
                    currentElement.append(maskingString);
                    pos = pos + maskingString.length();
                }
                else {
                    isQuoted = !isQuoted;
                }
            }
            else if (equalsStringPart(input, separatorString, pos)) {
                pos = pos + separatorString.length();
                if (isQuoted) {
                    currentElement.append(separatorString);
                }
                else {
                    value.add(currentElement.toString());
                    currentElement.setLength(0);
                    pos = skipSpaces(input, pos);
                }
            }
            else {
                currentElement.append(input.charAt(pos));
                pos++;
            }
        }
        if (input.length() > 0) {
            value.add(currentElement.toString());
        }

        inputObservable.fireInputChanged();
        validationCache.setDirty();
    }

    private static boolean equalsStringPart(final String text, final String search, final int pos) {
        if (pos + search.length() > text.length()) {
            return false;
        }

        int index = 0;
        while (pos + index < text.length() && index < search.length()) {
            if (text.charAt(pos + index) != search.charAt(index)) {
                return false;
            }
            index++;
        }

        return true;
    }

    private static int skipSpaces(final String text, final int pos) {
        int result = pos;
        while (result < text.length() && text.charAt(result) == ' ') {
            result++;
        }
        return result;
    }

    private final class TextFieldWithButtonLayouterFactory implements ILayoutFactory<ILayouter> {

        @Override
        public ILayouter create(final IContainer container) {
            return new TextFieldWithButtonLayouter(container);
        }

    }

    private final class TextFieldWithButtonLayouter implements ILayouter {

        private final IContainer container;

        private final Dimension preferredSize;
        private final Dimension minSize;
        private final Dimension maxSize;

        private final int buttonWidth;

        private TextFieldWithButtonLayouter(final IContainer container) {

            this.container = container;

            final Dimension fieldSize = textField.getPreferredSize();

            if (editButton.getIcon() != null) {
                this.buttonWidth = fieldSize.getHeight();
            }
            else {
                this.buttonWidth = editButtonContainer.getPreferredSize().getWidth();
            }

            this.preferredSize = new Dimension(fieldSize.getWidth() + buttonWidth, fieldSize.getHeight());
            this.minSize = new Dimension(buttonWidth + 4, fieldSize.getHeight());
            this.maxSize = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        @Override
        public void layout() {
            final Rectangle clientArea = container.getClientArea();

            final Dimension clientSize = clientArea.getSize();
            final int clientWidht = clientSize.getWidth();
            final int clientHeight = clientSize.getHeight();

            final int newButtonWidth;
            if (editButton.getIcon() != null) {
                newButtonWidth = clientHeight;
            }
            else {
                newButtonWidth = buttonWidth;
            }
            final int textFieldWidth = clientWidht - newButtonWidth;

            editButtonContainer.setPosition(clientArea.getX() + textFieldWidth, clientArea.getY());
            editButtonContainer.setSize(new Dimension(newButtonWidth, clientHeight));

            textField.setPosition(clientArea.getX(), clientArea.getY());
            textField.setSize(new Dimension(textFieldWidth, clientHeight));
        }

        @Override
        public void invalidate() {}

        @Override
        public Dimension getPreferredSize() {
            return preferredSize;
        }

        @Override
        public Dimension getMinSize() {
            return minSize;
        }

        @Override
        public Dimension getMaxSize() {
            return maxSize;
        }
    }

    private final class EditButtonConatinerLayouterFactory implements ILayoutFactory<ILayouter> {

        @Override
        public ILayouter create(final IContainer container) {
            return new EditButtonConatinerLayouter(container);
        }

    }

    private final class EditButtonConatinerLayouter implements ILayouter {

        private final IContainer container;

        private final Dimension preferredSize;

        private EditButtonConatinerLayouter(final IContainer container) {

            this.container = container;

            this.preferredSize = editButton.getPreferredSize();
        }

        @Override
        public void layout() {
            final Rectangle clientArea = container.getClientArea();

            final Dimension clientSize = clientArea.getSize();
            final int clientWidht = clientSize.getWidth();
            final int clientHeight = clientSize.getHeight();

            editButton.setPosition(clientArea.getX() - 1, clientArea.getY() - 1);
            editButton.setSize(new Dimension(clientWidht + 2, clientHeight + 2));
        }

        @Override
        public void invalidate() {}

        @Override
        public Dimension getPreferredSize() {
            return preferredSize;
        }

        @Override
        public Dimension getMinSize() {
            return preferredSize;
        }

        @Override
        public Dimension getMaxSize() {
            return preferredSize;
        }
    }

}
