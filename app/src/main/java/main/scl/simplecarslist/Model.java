package main.scl.simplecarslist;

/**
 * Created by vhoerzi16 on 07.03.2019.
 */

public class Model {
    private final String modelName;

    public Model(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
