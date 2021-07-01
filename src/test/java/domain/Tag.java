package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class Tag {
    @Nullable
    private Integer id;

    @Nullable
    private String name;

    @JsonCreator
    public Tag(
            @JsonProperty("id") @Nullable Integer id,
            @JsonProperty("name") @Nullable String name) {
        this.id = id;
        this.name = name;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        return name != null ? name.equals(tag.name) : tag.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
