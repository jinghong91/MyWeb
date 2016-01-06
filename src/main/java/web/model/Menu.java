package web.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MENU")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_MENU")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URL")
    private String url;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "HAS_SUBMENU")
    private boolean hasSubmenu;

    @ManyToOne
    @JoinColumn(name = "PARENT_MENU_ID")
    private Menu parentMenu;

    @OneToMany(mappedBy="parentMenu")
    private List<Menu> subMenuList = new ArrayList<Menu>();

    @Column(name = "ORDER")
    private int order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasSubmenu() {
        return hasSubmenu;
    }

    public void setHasSubmenu(boolean hasSubmenu) {
        this.hasSubmenu = hasSubmenu;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public List<Menu> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<Menu> subMenuList) {
        this.subMenuList = subMenuList;
    }
}
