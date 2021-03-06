package pl.kflorczyk.onlineshopbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonFilter("FeatureDefinition")
public class FeatureDefinition {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_group_id")
    private FeatureGroup featureGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_logic_id")
    @JsonBackReference
    private CategoryLogic categoryLogic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_definition_id")
    private List<FeatureValue> featureValueDefinitions = new ArrayList<>();

    private boolean multipleValues;

    private String name;

    private boolean filterable;

    private boolean visible;

    private boolean visibleInList;

    @Transient
    @JsonIgnore
    private boolean dummyPriceFilter;

    public FeatureDefinition() {}

    public FeatureDefinition(String name, FeatureGroup featureGroup) {
        this(name, featureGroup, false);
    }

    public FeatureDefinition(String name, FeatureGroup featureGroup, boolean filterable) {
        this(name, featureGroup, filterable, false);
    }

    public FeatureDefinition(String name, FeatureGroup featureGroup, boolean filterable, boolean multipleValues) {
        this(name, featureGroup, filterable, multipleValues, true);
    }

    public FeatureDefinition(String name, FeatureGroup featureGroup, boolean filterable, boolean multipleValues, boolean visible) {
        this.name = name;
        this.featureGroup = featureGroup;
        this.filterable = filterable;
        this.multipleValues = multipleValues;
        this.visible = visible;
    }

    public static FeatureDefinition forPriceFilter() {
        FeatureDefinition fd = new FeatureDefinition();
        fd.dummyPriceFilter = true;
        return fd;
    }

    public boolean isVisibleInList() {
        return visibleInList;
    }

    public void setVisibleInList(boolean visibleInList) {
        this.visibleInList = visibleInList;
    }

    public void setDummyPriceFilter(boolean dummyPriceFilter) {
        this.dummyPriceFilter = dummyPriceFilter;
    }

    public boolean isDummyPriceFilter() {
        return dummyPriceFilter;
    }

    public static FeatureDefinition ofID(long id) {
        FeatureDefinition featureDefinition = new FeatureDefinition();
        featureDefinition.setId(id);
        return featureDefinition;
    }

    public boolean isMultipleValues() {
        return multipleValues;
    }

    public FeatureGroup getFeatureGroup() {
        return featureGroup;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setCategoryLogic(CategoryLogic categoryLogic) {
        this.categoryLogic = categoryLogic;
    }

    public CategoryLogic getCategoryLogic() {
        return categoryLogic;
    }

    public boolean isFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

    public List<FeatureValue> getFeatureValueDefinitions() {
        return featureValueDefinitions;
    }

    public void setFeatureValueDefinitions(List<FeatureValue> featureValueDefinitions) {
        this.featureValueDefinitions = featureValueDefinitions;
    }

    public void addFeatureValueDefinition(FeatureValue featureValue) {
        featureValueDefinitions.add(featureValue);
    }

    @Override
    public String toString() {
        return String.format("%d", id);
        // when JSON parses Map<FeatureDefinition, FeatureValueGroup>,
        // the key for FeatureDefinition object is its .toString()
        // look at parsing CategoryView
    }

    public boolean isVisible() {
        return visible;
    }

    public void setFeatureGroup(FeatureGroup featureGroup) {
        this.featureGroup = featureGroup;
    }

    public void setMultipleValues(boolean multipleValues) {
        this.multipleValues = multipleValues;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
