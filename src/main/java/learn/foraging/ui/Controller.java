package learn.foraging.ui;

import learn.foraging.domain.ForageService;
import learn.foraging.domain.ForagerService;
import learn.foraging.domain.ItemService;
import learn.foraging.domain.Result;
import learn.foraging.models.Category;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import learn.foraging.models.Item;

import java.time.LocalDate;
import java.util.List;

public class Controller {

    private final ForagerService foragerService;
    private final ForageService forageService;
    private final ItemService itemService;
    private final View view;

    public Controller(ForagerService foragerService, ForageService forageService, ItemService itemService, View view) {
        this.foragerService = foragerService;
        this.forageService = forageService;
        this.itemService = itemService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Sustainable Foraging");
        try {
            runAppLoop();
        } catch (Exception ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_FORAGES_BY_DATE:
                    viewByDate();
                    break;
                case VIEW_FORAGER:
//
                    viewForager();
                    break;
                case VIEW_ITEMS:
                    viewItems();
                    break;
                case ADD_FORAGE:
                    addForage();
                    break;
                case ADD_FORAGER:
//                    view.displayStatus(false, "NOT IMPLEMENTED");
                    addForager();
                    view.enterToContinue();
                    break;
                case ADD_ITEM:
                    addItem();
                    break;
                case VIEW_REPORTS:
                    viewReports();
                    break;

            }
        } while (option != MainMenuOption.EXIT);
    }

    // top level menu
    private void viewByDate() {
        LocalDate date = view.getForageDate();
        List<Forage> forages = forageService.findByDate(date);
        view.displayForages(forages);
        view.enterToContinue();
    }

    private void viewItems() {
        view.displayHeader(MainMenuOption.VIEW_ITEMS.getMessage());
        Category category = view.getItemCategory();
        List<Item> items = itemService.findByCategory(category);
        view.displayHeader("Items");
        view.displayItems(items);
        view.enterToContinue();
    }

    private void addForage() {
        view.displayHeader(MainMenuOption.ADD_FORAGE.getMessage());
        Forager forager = getForager();
        if (forager == null) {
            return;
        }
        Item item = getItem();
        if (item == null) {
            return;
        }
        Forage forage = view.makeForage(forager, item);
        Result<Forage> result = forageService.add(forage);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Forage %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }

    private void addItem() {
        Item item = view.makeItem();
        Result<Item> result = itemService.add(item);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Item %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }

//    add forager
    private void addForager() {
        view.displayHeader(MainMenuOption.ADD_FORAGER.getMessage());
        Forager forager = view.addNewForager();
        Result<Forager> result = foragerService.add(forager);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Forager %s created.", result.getPayload().getId());
            view.displayStatus(true, successMessage);
        }
    }




    //    view Forager switch case (asks user to choose how to view forager)
    private void viewForager() {
        view.displayHeader(MainMenuOption.VIEW_FORAGER.getMessage());
        switch (view.selectForagerOption()) {
            case 1:
                viewForagerByState();
//                System.out.println("Case1");
                view.enterToContinue();
                break;
            case 2:
                System.out.println("Case2");
                view.enterToContinue();
//                viewForagerByLastName();
                break;
            case 3:
                System.out.println("Case3");
                view.enterToContinue();
//                viewForagerById();

                break;
        }

    }

    private void viewReports() {
        view.displayHeader(MainMenuOption.VIEW_REPORTS.getMessage());
        switch (view.selectReportOption()) {
            case 1:
                view.displayDayKgReport(forageService.reportOfItemAndKgForGivenDay(view.getForageDate()));
                view.enterToContinue();
                break;
            case 2:
                view.displayDayValueReport(forageService.reportOfItemAndValueForGivenDay(view.getForageDate()));
                view.enterToContinue();
                break;
        }
    }



    // support methods
    private Forager getForager() {
        String lastNamePrefix = view.getForagerNamePrefix();
        List<Forager> foragers = foragerService.findByLastName(lastNamePrefix);
        return view.chooseForager(foragers);
    }
    private void viewForagerByState() {  // Change return type to void
        String state = view.getForagerState();
        List<Forager> foragers = foragerService.findByState(state);
        view.displayForagers(foragers); // No return needed
    }


    private Item getItem() {
        Category category = view.getItemCategory();
        List<Item> items = itemService.findByCategory(category);
        return view.chooseItem(items);
    }
}
