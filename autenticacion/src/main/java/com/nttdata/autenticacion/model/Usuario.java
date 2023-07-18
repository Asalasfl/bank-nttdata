package  com.nttdata.autenticacion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "usuarios") // Nombre de la colección en MongoDB
public class Usuario {

    @Id
    private String id; // Cambiar a un tipo compatible con el ID de MongoDB (por ejemplo, String)

    private String usuario;
    private String password;
    private String rol;
}
