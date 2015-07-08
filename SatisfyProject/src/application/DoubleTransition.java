package application;

import javafx.animation.Transition;
import javafx.beans.property.*;
import javafx.util.Duration;


public class DoubleTransition extends Transition {

    private DoubleProperty _dp;
    private ObjectProperty<Duration> duration;
    private static final Duration DEFAULT_DURATION = Duration.millis(400);
    private DoubleProperty fromValue;
    private DoubleProperty toValue;
    private DoubleProperty byValue;
    
	public DoubleTransition(Duration duration, DoubleProperty aDP)
	{
	    setDuration(duration);
	    setCycleDuration(duration);
	    setDoubleProperty(aDP);
	}
	
	public final void setDuration(Duration value)
	{
	    if(duration!=null || (!DEFAULT_DURATION.equals(value)))
	        durationProperty().set(value);
	}

	public Duration getDuration()  { return (duration == null)? DEFAULT_DURATION : duration.get(); }
	
	public final ObjectProperty<Duration> durationProperty()
	{
	    if (duration == null) {
	        duration = new ObjectPropertyBase<Duration>(DEFAULT_DURATION) {
	            public void invalidated()  { setCycleDuration(getDuration()); }
	            public Object getBean()  { return DoubleTransition.this; }
	            public String getName() { return "duration"; }
	        };
	    }
	    return duration;
	}
	
	 DoubleProperty getDoubleProperty()  { return _dp; } 
	
	
	protected void setDoubleProperty(DoubleProperty aDP)  { _dp = aDP; }
	
	public final void setFromValue(double aValue)  { fromValueProperty().set(aValue); }
	
	public final Object getFromValue()  { return fromValueProperty().get(); }
	
	public final DoubleProperty fromValueProperty()
	{
	    if(fromValue == null) {
	        double value = getDoubleProperty().get();
	        fromValue = new SimpleDoubleProperty(this, "fromValue", value);
	    }
	    return fromValue;
	}

	
	public final Object getToValue()  { return toValueProperty().get(); }
	
	public final void setToValue(double value)  { toValueProperty().set(value); }
	
	public DoubleProperty toValueProperty()
	{
	    if(toValue==null) toValue = new SimpleDoubleProperty(this, "toValue");
	    return toValue;
	}
	
	public final Object getByValue()  { return byValueProperty().get(); }
	
	public final void setByValue(double value)  { byValueProperty().set(value); }
	
	public final DoubleProperty byValueProperty()
	{
	    if (byValue == null) byValue = new SimpleDoubleProperty(this, "byValue");
	    return byValue;
	}
	
	protected void interpolate(double aFraction)
	{
	    double fromValue = (Double)getFromValue();
	    double toValue = (Double)getToValue();
	    double value = getInterpolation(aFraction, fromValue, toValue);
	    getDoubleProperty().set(value);
	}
	
	private static double getInterpolation(double aFraction, double aStart, double anEnd)
	{ return aStart + (anEnd-aStart)*aFraction; }

}