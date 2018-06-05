package ch.supsi.dti.i2b.shrug.optitravel.ui;

import com.jfoenix.validation.base.ValidatorBase;

public class PlaceValidator extends ValidatorBase {

    public PlaceValidator(String message) {
        super(message);
    }

    @Override
    protected void eval() {
        if(!(srcControl.get() instanceof AutoCompleteTextField))
            throw new RuntimeException("PlaceValidator cannot be used to validate field " + srcControl.get().toString());
        else
            if(((AutoCompleteTextField) srcControl.get()).getPlace() == null) hasErrors.set(true);
            else hasErrors.set(false);
    }
}
