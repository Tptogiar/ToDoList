package main.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Tptogiar
 * @creat 2021-03-24-12:04
 */
public class ItemLabel implements Serializable {

    private String itemLabelType;


    public ItemLabel(String itemLabelType) {
        this.itemLabelType = itemLabelType;
    }

    public String getItemLabelType() {
        return itemLabelType;
    }

    @Override
    public String toString() {
        return itemLabelType;
    }

    /**
     * @Author: Tptogiar
     * @Description: 重写equals和hashCode方法，以方便后面此类的HashSet集合等调用查找方法等
     * @Date: 2021/3/24-14:12
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemLabel itemLabel = (ItemLabel) o;
        return Objects.equals(itemLabelType, itemLabel.itemLabelType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemLabelType);
    }

}


