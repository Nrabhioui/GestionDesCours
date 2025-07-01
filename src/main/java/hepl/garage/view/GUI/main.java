package hepl.garage.view.GUI;

import hepl.garage.controller.controllerClass;

public class main{
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        JDialogIdentification identificationDialog = new JDialogIdentification(ui, "Identification", true);
        new controllerClass(ui, identificationDialog);
    }
}
