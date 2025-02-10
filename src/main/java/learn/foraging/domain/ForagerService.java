package learn.foraging.domain;

import learn.foraging.data.ForagerRepository;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;

import java.util.List;

public class ForagerService {

    private final ForagerRepository repository;

    public ForagerService(ForagerRepository repository) {
        this.repository = repository;
    }

    public List<Forager> findByState(String stateAbbr) {
        return repository.findByState(stateAbbr);
    }

    public List<Forager> findByLastName(String prefix) {
        return repository.findByLastName(prefix);
    }
public Result<Forager> validateNulls(Forager forager){

    Result<Forager> result = new Result<>();

    if (forager == null) {
        result.addErrorMessage("Nothing to save.");
        return result;
    }

    if (forager.getFirstName() == null || forager.getFirstName().isBlank()) {
        result.addErrorMessage("First name is required.");
    }

    if (forager.getLastName() == null || forager.getLastName().isBlank()) {
        result.addErrorMessage("Last name is required.");
    }

    if (forager.getState() == null || forager.getState().isBlank()) {
        result.addErrorMessage("State is required.");
    }


    return result;
}

public Result<Forager> add(Forager forager) {
    Result<Forager> result = validateNulls(forager);
    if (!result.isSuccess()) {
        return result;
    }

    result.setPayload(repository.add(forager));
    return result;
}

}
