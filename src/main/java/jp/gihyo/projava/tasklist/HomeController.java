package jp.gihyo.projava.tasklist;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
//HTMLを返したい場合は@Controller

public class HomeController{
    record TaskItem(String id,String task,String deadline,boolean done){}
    private List<TaskItem> taskItems= new ArrayList<>();

    @GetMapping("list")
    String listItems(Model model){
        //キーが"taslList"、値がtaskItemsフィールドの値という属性を追加
        //home.html側では、${taskList}部分が、taskItemsの中身であるListオブジェクトに置き換わる
        model.addAttribute("taskList",taskItems);
        return "home";
    }

    @GetMapping("/add")
    String addItem(@RequestParam("task")String task,
                   @RequestParam("deadline") String deadline){
        String id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(id,task,deadline,false);
        taskItems.add(item);

        return "redirect:/list";
    }


    /**
     * ビューを構成するHTMLはテンプレートエンジンが作成する。
     * そのためエンドポイントのメソッドには、HTTPレスポンスの本体そのものではなく、
     * 対応するビュー名を文字列で返すようにする。
     * ビュー名は、HTMLテンプレートのファイル名から拡張子を除いたものを使う（例：hello)

    @RequestMapping(value = "/hello")
    String hello(Model model){
        //modelクラスはJavaプログラムとHTMLテンプレートの間で値を受け渡す役割を担う
        //addAttributeメソッドで属性を設定
        //hello.htmlの${time}部分に現在時刻を渡して置き換える
        model.addAttribute("time", LocalDateTime.now());
        return  "hello";
    }**/
}