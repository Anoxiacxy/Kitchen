package repository;

public interface SequenceRepository {
    public int getNext(String seqName) throws Exception;
}
