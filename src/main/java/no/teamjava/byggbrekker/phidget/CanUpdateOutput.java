package no.teamjava.byggbrekker.phidget;

/**
 * @author Olav Jensen
 * @since 03.nov.2009
 */
public interface CanUpdateOutput {

	void setOutputState(int output, boolean condition);
}
