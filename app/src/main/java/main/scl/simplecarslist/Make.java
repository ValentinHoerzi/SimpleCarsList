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
}
