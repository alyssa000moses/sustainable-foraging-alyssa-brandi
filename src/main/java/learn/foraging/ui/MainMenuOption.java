package learn.foraging.ui;

public enum MainMenuOption {

    EXIT("Exit"),
    VIEW_FORAGES_BY_DATE("View Forages By Date"),
    VIEW_FORAGER("View Forager"),
    VIEW_ITEMS("View Items"),
    ADD_FORAGE("Add a Forage"),
    ADD_FORAGER("Add a Forager"),
    ADD_ITEM("Add an Item"),
    VIEW_REPORTS("View Reports");

    private String message;

    private MainMenuOption(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
