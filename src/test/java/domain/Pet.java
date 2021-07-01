package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class Pet {
    @Nullable
    private final Long id;

    @Nullable
    private final Category category;

    @Nullable
    private final String name;

    @Nullable
    private final List<String> photoUrls;

    @Nullable
    private final List<Tag> tags;

    @Nullable
    private final String status;

    @JsonCreator
    public Pet(@JsonProperty("id") @Nullable Long id,
               @JsonProperty("category") @Nullable Category category,
               @JsonProperty("name") @Nullable String name,
               @JsonProperty("photoUrls") @Nullable List<String> photoUrls,
               @JsonProperty("tags") @Nullable List<Tag> tags,
               @JsonProperty("status") @Nullable String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    private Pet(Builder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.name = builder.name;
        this.photoUrls = builder.photoUrls;
        this.tags = builder.tags;
        this.status = builder.status;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    @Nullable
    public List<Tag> getTags() {
        return tags;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public static class Builder {

        private Long id;
        private Category category;
        private String name;
        private List<String> photoUrls;
        private List<Tag> tags;
        private String status;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPhotoUrls(List<String> photoUrls) {
            this.photoUrls = photoUrls;
            return this;
        }

        public Builder withTags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Pet build() {
            return new Pet(this);
        }
    }
}