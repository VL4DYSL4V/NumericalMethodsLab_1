package dto;

import lombok.Data;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;

@Data
public class ParametersDto {

    private final Interval interval;

    private final double precision;

}
