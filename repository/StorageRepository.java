package repository;

import entitys.Storage;

public interface StorageRepository {
    void save(Storage storage) throws Exception;

    Storage load() throws Exception;
}
