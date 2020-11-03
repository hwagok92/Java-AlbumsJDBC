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
			System.out.println("\n======메뉴 선택하기======");
			System.out.println("1.모든 정보 조회");
			System.out.println("2.조건 검색");
			System.out.println("3.가격 범위조건 검색(가격:내림차순, 가수:오름차순)");
			System.out.println("4.앨범 수정");
			System.out.println("5.앨범 삭제");
			System.out.println("6.앨범 추가");
			System.out.println("7.정렬");
			System.out.println("8.프로그램 종료");
			System.out.print("메뉴 선택 >> ");

			int menu = sc.nextInt() ;

			switch (menu) {
			case 1: // 모든상품조회
				ArrayList<AlbumsBean> lists = dao.getAllAlbums() ; 
				ShowAlbums( lists );
				break;

			case 2://  조건검색
				getAlbumBySearch(); 
				break;

			case 3:// 가격 범위조건 검색
				getAlbumByRange();
				break;

			case 4:// 상품수정
				updateData() ;
				break;

			case 5:// 상품삭제
				deleteData() ;
				break;

			case 6://상품추가
				insertData() ;
				break;		

			case 7: // 정렬
				align();
				break;

			case 8://종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0) ;
				break;

			default:
				break;
			}			
		}
	}

	private void align() {

		String col=null, way=null;
		System.out.println("정렬할 항목 선택하세요.");
		System.out.print("번호:1      노래제목:2      가수명:3         번호입력>> ");
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
				System.out.println("잘못입력함");
				return ;
		}
		
		System.out.println("정렬방법 선택하세요");
		System.out.print("오름차순:1     내림차순:2       번호입력>> ");
		int align_way = sc.nextInt();
		
		switch(align_way){
		case 1 : way="asc";
				break;
		case 2 : way="desc";
				break;			
		default : System.out.println("잘못 입력하셨음");
				return;				
		}
		
		ArrayList<AlbumsBean> lists = dao.align(col,way);
		ShowAlbums(lists);
		
	}

	private void deleteData() {
		System.out.print("삭제할 번호 입력 : ");
		int num = sc.nextInt();
		int cnt = dao.deleteData(num);
		if(cnt<=0)
			System.out.println("delete 실패");
		else
			System.out.println("delete 성공");
	}

	private void updateData() throws IOException {
		
		int price = 0, num ;
		String song, singer,company,pub_day;	

		System.out.print("수정할 번호 입력 : ");
		num = sc.nextInt(); 
		
		System.out.print("노래제목 입력 : ");
		song = br.readLine(); // 붉은 노을

		System.out.print("가수명 입력 : ");
		singer = br.readLine();

		System.out.print("회사명 입력 : ");
		company = br.readLine();
		do {
			try {
				System.out.print("가격 입력 : ");
				price = Integer.parseInt(br.readLine()); // 문자열=>정수
				break;
			}catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요");
			}
		}while(true);
		
		System.out.print("입고일자 입력 : ");
		pub_day = br.readLine();

		AlbumsBean bean = new AlbumsBean(num,song,singer,company,price,pub_day);

		int cnt = dao.updateData(bean);
		if(cnt<=0)
			System.out.println("update 실패");
		else
			System.out.println("update 성공");
	}

	private void insertData() throws IOException {
		int price = 0 ;
		String song, singer,company,pub_day;	

		System.out.println("번호는 시퀀스로 입력됩니다(생략)");
		System.out.print("노래제목 입력 : ");
		song = br.readLine(); // 붉은 노을

		System.out.print("가수명 입력 : ");
		singer = br.readLine();

		System.out.print("회사명 입력 : ");
		company = br.readLine();
		do {
			try {
				System.out.print("가격 입력 : ");
				price = Integer.parseInt(br.readLine()); // 문자열=>정수
				break;
			}catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요");
			}
		}while(true);
		
		System.out.print("입고일자 입력 : ");
		pub_day = br.readLine();

		AlbumsBean bean = new AlbumsBean(0,song,singer,company,price,pub_day);

		int cnt = dao.insertData(bean);
		if(cnt<=0)
			System.out.println("insert 실패");
		else
			System.out.println("insert 성공");

	} // insertData

	private void getAlbumByRange() {

		int from, to;				

		System.out.print("시작등수 입력 : ");
		from = sc.nextInt();

		System.out.print("끝등수 입력 : "); 
		to = sc.nextInt();

		ArrayList<AlbumsBean> lists = dao.getAlbumByRange(from,to);
		ShowAlbums(lists);
	}

	private void getAlbumBySearch() {

		String col=null;			
		System.out.println("제목검색:1     가수검색:2     회사검색:3    ");
		System.out.print("검색할 항목을 선택 : ");
		int search_num = sc.nextInt();

		switch(search_num){
		case 1 : System.out.print("검색할 제목 입력 : ");
		col = "song";
		break;

		case 2 : System.out.print("검색할 가수 입력 : ");
		col = "singer";
		break;

		case 3 : System.out.print("검색할 회사 입력 : ");
		col = "company";
		break;

		default : System.out.println("잘못 입력하셨음");
		return;
		}

		String search_word = sc.next();
		ArrayList<AlbumsBean> find_lists = 
				dao.getAlbumBySearch( col, search_word ) ;

		if(find_lists.size() == 0)
			System.out.println("해당 단어는 존재하지 않음");
		else
			ShowAlbums(find_lists);


	}// getAlbumBySearch

	private void ShowAlbums(ArrayList<AlbumsBean> lists) {
		String title = "번호\t" + "노래제목\t" + "가수\t" + "회사\t" + "가격\t" + "발매일\t" ; 
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








