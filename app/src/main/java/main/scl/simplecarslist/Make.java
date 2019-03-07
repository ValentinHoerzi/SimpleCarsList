package main.scl.simplecarslist;

/**
 * Created by vhoerzi16 on 07.03.2019.
 */

public class Make {
    private final String makeName;

    public Make(String makeName) {
        this.makeName = makeName;
    }

    public String getMakeName() {
        return makeName;
    }

    @Override
    public String toString() {
        return makeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Make make = (Make) o;

        return makeName != null ? makeName.equals(make.makeName) : make.makeName == null;
    }

    @Override
    public int hashCode() {
        return makeName != null ? makeName.hashCode() : 0;
    }
}
