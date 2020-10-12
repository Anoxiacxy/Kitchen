package repository;

import entitys.HistoryFoodPrice;

import java.util.List;

public interface HistoryFoodPriceRepository {
    List<HistoryFoodPrice> getAll(int foodID) throws Exception;

    HistoryFoodPrice getLastPrice(int foodId) throws Exception;

    void save(HistoryFoodPrice price) throws Exception;
}
