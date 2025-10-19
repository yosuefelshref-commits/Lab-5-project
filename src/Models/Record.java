package Models;

public abstract class Record {
    public abstract String lineRepresentation();
    public abstract String getSearchKey();
    @Override
    public String toString() {
        return lineRepresentation();
    }
}
