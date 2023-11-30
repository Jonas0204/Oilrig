package assets;

public class ShipBig extends Ship{

    //@author Matthias
    @Override
    public boolean checkCapacity(int currently, int numberofworker) {
        if ((currently + numberofworker) <= 100) {
            return true;
        }
        return false;
    }
}
