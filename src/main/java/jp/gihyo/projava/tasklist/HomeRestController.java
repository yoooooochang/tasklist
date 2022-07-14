package jp.gihyo.projava.tasklist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController /** クラスをコントローラーとして宣言　
 @RestContorollerはユーザーインターフェースとしてのHTMLを返さないWebアプリケーションを作る場合に使われる*/
public class HomeRestController {
 //レコード型としてタスク情報として保持したい内容を宣言
     record  TaskItem(String id, String task, String deadline, boolean done){}
 //複数のタスクを登録するために複数のTaskItemオブジェクトを格納するフィールドを用意
    private List<TaskItem> taskItems = new ArrayList<>();



    //タスクを追加する、登録タスクを一覧表示するエンドポイントの作成　addItemメソッドを宣言している
    @GetMapping("/restadd")
    /**
     * @RequestParam：このアノテーションが付けられた引数は、自動的にHTTPリクエストのパラメーターと関連づけられる
     * ＝HTTPリクエストのtaskパラメータの値とdeadlineパラメーターの値が、それぞれ
     * 　addItemメソッドが呼び出される際に第１引数、第２引数として渡される
     */
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline){
        //ランダムに一意の値を生成。UUIDで生成される文字列は３２桁⇨（都合上）先頭８文字のみ切り出している。
        String id = UUID.randomUUID().toString().substring(0,8);
        //受け取った２つの引数を使って新しくTaskItemオブジェクトを作成
        //それをtaskItemsフィールドに追加　コンストラクタに４つの引数を渡す。
        //タスク作成時は必ず未完了なので、doneはfalseとする
        TaskItem item = new TaskItem(id,task,deadline,false);
        taskItems.add(item);

        return"タスクを追加しました。";
    }
    //タスクを一覧表示するエンドポイントの作成
    @GetMapping("/restlist")
    String listItems(){
    //タスク情報はtaskItemsフィールドに格納されている
    //⇨taskItemsの各要素をtoStringメソッドで文字列に変換し、それを結合して返すよう記述
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(","));
        return  result;
    }

    /**
     @RequestMapping(value="/resthello")
    クライアントからのリクエストを処理するメソッドであることを表すアノテーション
    リクエストはget,post両方に対応していて、明示的に受け取るリクエストの指定も可能
    ＝hell()はクライアントからのリクエストがあった場合に呼び出されるメソッド
    ⇆@GetMappingはGETメソッド専用のアノテーション
    value=は省略可能。パスを指定している
    static ではなくインスタンスメソッドとして宣言しなければならない
    ⇨SpringBoot で使用するクラスのメソッドはインスタンスメソッドとして宣言するのが基本
    String hello(){
    return """
    hello
    It works
    time:%s
    """.formatted(LocalDateTime.now());
    }*/
}


