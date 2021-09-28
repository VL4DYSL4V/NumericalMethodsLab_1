package state;

import framework.state.AbstractApplicationState;
import framework.state.StateHelper;
import framework.utils.ConsoleUtils;
import framework.variable.holder.VariableHolder;
import framework.variable.holder.VariableHolderAware;
import lombok.Getter;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;

@Getter
public class LaboratoryState extends AbstractApplicationState
    implements VariableHolderAware {

    private final  PolynomialFunction function = new PolynomialFunction(new double[]{-8, -14, -3, 1});

    private Interval interval;

    private double precision = 1e-4;

    private VariableHolder variableHolder;

    @Override
    protected void initVariableNameToSettersMap() {
        this.variableNameToSetter.put("precision", (name, value) -> StateHelper.defaultSet(name, "precision",
                value, Double.class, (obj) -> (Double) obj, this::setPrecision
        ));
        this.variableNameToSetter.put("interval", (name, value) -> StateHelper.defaultSet(name, "interval",
                value, Interval.class, (obj) -> (Interval) obj, this::setInterval
        ));
    }

    @Override
    protected void initVariableNameToGettersMap() {
        this.variableNameToGetter.put("function", this::getFunction);
        this.variableNameToGetter.put("precision", this::getPrecision);
        this.variableNameToGetter.put("interval", this::getInterval);
    }

    public void setInterval(Interval interval) {
        if (interval == null) {
            ConsoleUtils.println("Interval must not be null");
            return;
        }
        this.interval = interval;
    }

    public void setPrecision(double precision) {
        if (precision < 0) {
            ConsoleUtils.println(variableHolder.getVariable("precision").getConstraintViolationMessage());
            return;
        }
        this.precision = precision;
    }

    @Override
    public void setVariableHolder(VariableHolder variableHolder) {
        if (variableHolder == null) {
            throw new NullPointerException("variableHolder must not be null");
        }
        this.variableHolder = variableHolder;
    }
}
