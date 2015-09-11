package song.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import song.repository.NewsItemRepository;

/**
 * Created by Song on 2015/6/12.
 */
@Controller
@SessionAttributes("newsList")
public class IndexController {
    @Autowired
    private NewsItemRepository newsItemRepository;

    /*
        跳转到主页
     */
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("newsList",newsItemRepository.findAll());
        return "home";
    }




    /*
        跳转到后台主页
     */
    @RequestMapping("/admin")
    public String index(){
        return  "index";
    }

}
