import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class AlbumsMain {
	AlbumsDao dao = new AlbumsDao();
	Scanner sc = new Scanner(System.in);
	BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));

	AlbumsMain() throws IOException{
		init(); 
	}

	public void init() throws IOException {
		while(true){
			System.out.println("\n======�޴� �����ϱ�======");
			System.out.println("1.��� ���� ��ȸ");
			System.out.println("2.���� �˻�");
			System.out.println("3.���� �������� �˻�(����:��������, ����:��������)");
			System.out.println("4.�ٹ� ����");
			System.out.println("5.�ٹ� ����");
			System.out.println("6.�ٹ� �߰�");
			System.out.println("7.����");
			System.out.println("8.���α׷� ����");
			System.out.print("�޴� ���� >> ");

			int menu = sc.nextInt() ;

			switch (menu) {
			case 1: // ����ǰ��ȸ
				ArrayList<AlbumsBean> lists = dao.getAllAlbums() ; 
				ShowAlbums( lists );
				break;

			case 2://  ���ǰ˻�
				getAlbumBySearch(); 
				break;

			case 3:// ���� �������� �˻�
				getAlbumByRange();
				break;

			case 4:// ��ǰ����
				updateData() ;
				break;

			case 5:// ��ǰ����
				deleteData() ;
				break;

			case 6://��ǰ�߰�
				insertData() ;
				break;		

			case 7: // ����
				align();
				break;

			case 8://����
				System.out.println("���α׷��� �����մϴ�.");
				System.exit(0) ;
				break;

			default:
				break;
			}			
		}
	}

	private void align() {

		String col=null, way=null;
		System.out.println("������ �׸� �����ϼ���.");
		System.out.print("��ȣ:1      �뷡����:2      ������:3         ��ȣ�Է�>> ");
		int align_num = sc.nextInt();
		
		switch(align_num) {
		case 1:
				col = "num";
				break;
		case 2:
				col = "song";
				break;
		case 3:
				col = "singer";
				break;
		default: 
				System.out.println("�߸��Է���");
				return ;
		}
		
		System.out.println("���Ĺ�� �����ϼ���");
		System.out.print("��������:1     ��������:2       ��ȣ�Է�>> ");
		int align_way = sc.nextInt();
		
		switch(align_way){
		case 1 : way="asc";
				break;
		case 2 : way="desc";
				break;			
		default : System.out.println("�߸� �Է��ϼ���");
				return;				
		}
		
		ArrayList<AlbumsBean> lists = dao.align(col,way);
		ShowAlbums(lists);
		
	}

	private void deleteData() {
		System.out.print("������ ��ȣ �Է� : ");
		int num = sc.nextInt();
		int cnt = dao.deleteData(num);
		if(cnt<=0)
			System.out.println("delete ����");
		else
			System.out.println("delete ����");
	}

	private void updateData() throws IOException {
		
		int price = 0, num ;
		String song, singer,company,pub_day;	

		System.out.print("������ ��ȣ �Է� : ");
		num = sc.nextInt(); 
		
		System.out.print("�뷡���� �Է� : ");
		song = br.readLine(); // ���� ����

		System.out.print("������ �Է� : ");
		singer = br.readLine();

		System.out.print("ȸ��� �Է� : ");
		company = br.readLine();
		do {
			try {
				System.out.print("���� �Է� : ");
				price = Integer.parseInt(br.readLine()); // ���ڿ�=>����
				break;
			}catch(NumberFormatException e) {
				System.out.println("���ڷ� �Է��ϼ���");
			}
		}while(true);
		
		System.out.print("�԰����� �Է� : ");
		pub_day = br.readLine();

		AlbumsBean bean = new AlbumsBean(num,song,singer,company,price,pub_day);

		int cnt = dao.updateData(bean);
		if(cnt<=0)
			System.out.println("update ����");
		else
			System.out.println("update ����");
	}

	private void insertData() throws IOException {
		int price = 0 ;
		String song, singer,company,pub_day;	

		System.out.println("��ȣ�� �������� �Էµ˴ϴ�(����)");
		System.out.print("�뷡���� �Է� : ");
		song = br.readLine(); // ���� ����

		System.out.print("������ �Է� : ");
		singer = br.readLine();

		System.out.print("ȸ��� �Է� : ");
		company = br.readLine();
		do {
			try {
				System.out.print("���� �Է� : ");
				price = Integer.parseInt(br.readLine()); // ���ڿ�=>����
				break;
			}catch(NumberFormatException e) {
				System.out.println("���ڷ� �Է��ϼ���");
			}
		}while(true);
		
		System.out.print("�԰����� �Է� : ");
		pub_day = br.readLine();

		AlbumsBean bean = new AlbumsBean(0,song,singer,company,price,pub_day);

		int cnt = dao.insertData(bean);
		if(cnt<=0)
			System.out.println("insert ����");
		else
			System.out.println("insert ����");

	} // insertData

	private void getAlbumByRange() {

		int from, to;				

		System.out.print("���۵�� �Է� : ");
		from = sc.nextInt();

		System.out.print("����� �Է� : "); 
		to = sc.nextInt();

		ArrayList<AlbumsBean> lists = dao.getAlbumByRange(from,to);
		ShowAlbums(lists);
	}

	private void getAlbumBySearch() {

		String col=null;			
		System.out.println("����˻�:1     �����˻�:2     ȸ��˻�:3    ");
		System.out.print("�˻��� �׸��� ���� : ");
		int search_num = sc.nextInt();

		switch(search_num){
		case 1 : System.out.print("�˻��� ���� �Է� : ");
		col = "song";
		break;

		case 2 : System.out.print("�˻��� ���� �Է� : ");
		col = "singer";
		break;

		case 3 : System.out.print("�˻��� ȸ�� �Է� : ");
		col = "company";
		break;

		default : System.out.println("�߸� �Է��ϼ���");
		return;
		}

		String search_word = sc.next();
		ArrayList<AlbumsBean> find_lists = 
				dao.getAlbumBySearch( col, search_word ) ;

		if(find_lists.size() == 0)
			System.out.println("�ش� �ܾ�� �������� ����");
		else
			ShowAlbums(find_lists);


	}// getAlbumBySearch

	private void ShowAlbums(ArrayList<AlbumsBean> lists) {
		String title = "��ȣ\t" + "�뷡����\t" + "����\t" + "ȸ��\t" + "����\t" + "�߸���\t" ; 
		System.out.println( title );

		for( AlbumsBean album : lists ){
			System.out.printf("%-5d\t %-8s\t %-5s\t %-6s\t %-5d\t %-10s\n", 
					album.getNum(),album.getSong(),
					album.getSinger(),album.getCompany(),
					album.getPrice(),album.getPub_day() );
		}		
	}

	public static void main(String[] args) throws IOException { 
		new AlbumsMain();
	}

}








