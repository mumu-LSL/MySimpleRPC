package RtLin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Lin
 * @Date 2022/5/12 - 14:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloObject implements Serializable {
    private Integer ID;
    private String message;
}