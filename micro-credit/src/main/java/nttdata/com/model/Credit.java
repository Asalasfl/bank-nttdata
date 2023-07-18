package nttdata.com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "credits")

/** A credit is a generic concept that can have different types, such as personal credit, business credit
 * establish a common foundation for all bank accounts and allow each account type to implement its own
 * specific behavior, providing flexibility and modularity
 * PERSONAL, ENTERPRISE for String type
 */
public  class Credit {
    @Id
    private String id;
    private String customerId;
    private String type;//personal un creidto, empresarial varios creditos
    private BigDecimal amount;
    private BigDecimal interestRate;
    private BigDecimal remainingAmount;
    @Field("paymentReferences")
    private List<Payment> paymentReferences; // List of payment IDs
}