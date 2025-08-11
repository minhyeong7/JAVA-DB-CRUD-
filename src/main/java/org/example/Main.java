package org.example;


import org.example.dao.UserDao;
import org.example.model.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDao dao = new UserDao(); //CRUD 기능 객체 생성
        Scanner sc =new Scanner(System.in); // 스캐너 객체 생성


        while(true){
            System.out.println("[1]조회 [2]추가 [3]이름수정 [4]삭제 [0]종료");
            int number = Integer.parseInt(sc.nextLine());

            if(number == 1){
                List<User> users = dao.getAll();
                for(User user:users){
                    System.out.println("아이디: "+user.getId()+" 이름: "+user.getName()+" 나이: "+user.getAge());
                }

            }

            else if(number ==2){
                System.out.println("이름입력: ");
                String name =sc.nextLine();
                System.out.println("나이입력 ");
                int age = Integer.parseInt(sc.nextLine());
                User user1= new User(name,age);
                dao.insert(user1);

            }

            else if(number ==3){
                System.out.println("수정할 아이디: ");
                int id=Integer.parseInt(sc.nextLine());
                System.out.println("변경 이름: ");
                String name= sc.nextLine();

                User user2 = new User();
                user2.setId(id);
                user2.setName(name);
                dao.updateName(user2);

            }

            else if(number == 4){
                System.out.println("삭제할 id: ");
                int id=Integer.parseInt(sc.nextLine());
                dao.delete(id);
                System.out.println("삭제 완료");
            }
            else if(number==0){
                System.out.println("종료되었습니다");
                break;
            }



        }

    }
}