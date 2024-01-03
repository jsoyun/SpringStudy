package hello.springmvc.itemservice.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * fast 빠른 배송, normal 일반, slow 느린
 */

@Data
@AllArgsConstructor
public class DeliveryCode {
    private String code;
    private String displayName;
}
