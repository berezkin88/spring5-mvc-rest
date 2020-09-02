package guru.springfamework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */
@Data
@AllArgsConstructor
public class CategoryListDTO {

    private List<CategoryDTO> categories;

}
