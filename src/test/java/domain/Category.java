package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class Category {
    @Nullable
    private final Integer id;

    @Nullable
    private final String name;

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @JsonCreator
    public Category(
            @JsonProperty("id") @Nullable Integer id,
            @JsonProperty("name") @Nullable String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        return name != null ? name.equals(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
