package song.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import song.model.Admin;
import song.model.NewsItem;
import song.repository.NewsDetailRepository;
import song.repository.NewsItemRepository;

import java.util.List;

/**
 * Created by Song on 2015/9/29.
 */
public class AdminUI extends CustomComponent implements View {

    public static final String NAME = "adminui";
    private Logger logger = org.slf4j.LoggerFactory.getLogger(AdminUI.class);


    private Table table ;

    private EditArticleForm articleForm = new EditArticleForm();

    private NewsItemRepository repository;
    private NewsDetailRepository detailRepository;


    public AdminUI(NewsItemRepository repository, NewsDetailRepository detailRepository) {
        this.repository = repository;
        this.detailRepository  = detailRepository;

    }

    public Table getTable() {
        return table;
    }

    public void AdminUI(){
    }



    private  void setUp() {
        setSizeFull();
        final List<NewsItem> newsItems = repository.findAll();
        table = new Table();
        table.addContainerProperty("Id",Long.class,null);
        table.addContainerProperty("Title",String.class,null);
        table.addContainerProperty("操作",Button.class,null);
        table.addContainerProperty("删除",Button.class,null);
        setUpTable(newsItems);
        VerticalLayout left = new VerticalLayout(new Label("文章列表"),table);
        left.setSizeFull();
        table.setSizeFull();
        left.setExpandRatio(table, 1);
        HorizontalLayout mainLayout = new HorizontalLayout(left, articleForm);
        articleForm.setSizeFull();
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);
        mainLayout.setExpandRatio(articleForm,1);
        setCompositionRoot(mainLayout);
    }


    public void refereshTable(){
        table.removeAllItems();
        setUpTable(repository.findAll());
    }


    private void setUpTable(List<NewsItem> newsItems){
        for(NewsItem item:newsItems){
            Long id = item.getId();
            String title = item.getTitle();
            Button change = new Button("修改",FontAwesome.EDIT);
            change.setData(id);
            change.setStyleName("friendly");
            change.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Long id = (Long) event.getButton().getData();
//                    getCurrent().getPage().setLocation("/news/updateNews?id="+id);
                    articleForm.edit(id,repository,detailRepository);
                }
            });
            Button delete  = new Button("删除",FontAwesome.TRASH_O);
            delete.setStyleName("danger");
            delete.setData(id);
            delete.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Long id = (Long) event.getButton().getData();
                    repository.delete(id);
                    table.removeItem(id);
                    Notification.show("Id:"+id+"，已经被删除了！");
                }
            });
            table.addItem(new Object[]{id,title,change,delete},id);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("登陆成功！");
        setUp();
    }
}
