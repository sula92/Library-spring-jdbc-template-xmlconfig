import dao.daoimpl.BookDAOImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("resources/Beans.xml");
        //MemberDAOImpl memberdaoimpl = (MemberDAOImpl) context.getBean("memberdaoimpl");

        BookDAOImpl bookDAO = (BookDAOImpl) context.getBean("bookdaoimpl");

       // MemberDAO memberDAO=new MemberDAOImpl();
       // memberdaoimpl.listMembers().stream().forEach(System.out::println);
        bookDAO.listBooks().stream().forEach(System.out::println);


    }

}
