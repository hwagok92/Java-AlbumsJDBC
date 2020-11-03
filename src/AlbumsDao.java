import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumsDao {

	private String driver = "oracle.jdbc.driver.OracleDriver" ;
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl" ;
	private String username = "jspid" ;
	private String password = "jsppw" ;
	private Connection conn = null ;
	PreparedStatement ps = null;
	ResultSet rs = null;

	AlbumsDao(){
		try {
			Class.forName(driver) ;			
		} catch (ClassNotFoundException e) {
			System.out.println("클래스가 발견되지 않습니다(jar 파일 누락)"); 
			e.printStackTrace();		
		}
	}

	private void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password) ;
		} catch (SQLException e) {
			System.out.println("커넥션 생성 오류");
			e.printStackTrace();
		}
	}

	public ArrayList<AlbumsBean> getAllAlbums() {

		getConnection() ;

		String sql = "select * from albums order by num" ;

		ArrayList<AlbumsBean> lists = new ArrayList<AlbumsBean>();

		try {
			ps = conn.prepareStatement(sql) ; 

			rs = ps.executeQuery() ;

			while(rs.next()){
				AlbumsBean album = new AlbumsBean() ;
				album.setNum( rs.getInt("num")) ;
				album.setSong( rs.getString("song") );
				album.setSinger( rs.getString("singer")) ; 
				album.setCompany( rs.getString("company") );
				album.setPrice( rs.getInt("price") ); 
				album.setPub_day( String.valueOf( rs.getDate("pub_day")));
				lists.add( album ) ;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			try {
				if(rs != null) {rs.close(); }
				if(ps != null) {ps.close(); }
				if(conn != null) {conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		return lists ;

	}// getAllAlbums

	public ArrayList<AlbumsBean> getAlbumBySearch(String col, String search_word) {

		getConnection();

		String sql = "select * from albums where lower(" + col +") like ? order by num asc";
		ArrayList<AlbumsBean> lists = new ArrayList<AlbumsBean>();

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+search_word.toLowerCase()+"%"); // %L% => %l%
			//			badgirL 
			//			helLo
			rs = ps.executeQuery();

			while(rs.next()){ 
				AlbumsBean album = new AlbumsBean() ;
				album.setNum( rs.getInt("num")) ;
				album.setSong( rs.getString("song") );
				album.setSinger( rs.getString("singer")) ; 
				album.setCompany( rs.getString("company") );
				album.setPrice( rs.getInt("price") ); 
				album.setPub_day( String.valueOf( rs.getDate("pub_day")));
				lists.add( album ) ;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {rs.close(); }
				if(ps != null) {ps.close(); }
				if(conn != null) {conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		return lists;
	}

	public ArrayList<AlbumsBean> getAlbumByRange(int from, int to) {

		getConnection();

		String sql = "select rownum,num,song,singer,company,price,pub_day,rank " + 
				" from(select rownum,num,song,singer,company,price,pub_day,rank() over(order by price desc, singer asc) as rank " + 
				" from albums) where rank between ? and ?";
		ArrayList<AlbumsBean> lists = new ArrayList<AlbumsBean>();

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, from);
			ps.setInt(2, to);

			rs = ps.executeQuery();

			while(rs.next()){ 
				AlbumsBean album = new AlbumsBean() ;
				album.setNum( rs.getInt("num")) ;
				album.setSong( rs.getString("song") );
				album.setSinger( rs.getString("singer")) ; 
				album.setCompany( rs.getString("company") );
				album.setPrice( rs.getInt("price") ); 
				album.setPub_day( String.valueOf( rs.getDate("pub_day")));
				lists.add( album ) ;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {rs.close(); }
				if(ps != null) {ps.close(); }
				if(conn != null) {conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		return lists;
	}

	public int insertData(AlbumsBean bean) {
		System.out.println(bean.getSong());
		//넘어오는 모든것 출력

		getConnection();

		String sql = "insert into albums(num, song, singer, company, price, pub_day) " ;
		sql += " values(albumseq.nextval, ?, ?, ?, ?, to_date(?, 'yyyy/mm/dd')) " ;
		int cnt = -1;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getSong());
			ps.setString(2, bean.getSinger());
			ps.setString(3, bean.getCompany());
			ps.setInt(4, bean.getPrice());
			ps.setString(5, bean.getPub_day());

			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(ps != null) {ps.close(); }
				if(conn != null) {conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}

		return cnt;
	}

	public int updateData(AlbumsBean bean) {
		System.out.println(bean.getNum());

		getConnection();

		String sql = "update albums set song=?, singer=?, company=?, price=?, pub_day=to_date(?, 'yyyy/mm/dd') " ;
		sql += " where num = ?" ;

		int cnt = -1;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getSong());
			ps.setString(2, bean.getSinger());
			ps.setString(3, bean.getCompany());
			ps.setInt(4, bean.getPrice());
			ps.setString(5, bean.getPub_day());
			ps.setInt(6, bean.getNum());
			
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(ps != null) {ps.close(); }
				if(conn != null) {conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}

		return cnt;
	}

	public int deleteData(int num) {
		getConnection();

		String sql = "delete from albums where num = ?" ;
		
		int cnt = -1;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(ps != null) {ps.close(); }
				if(conn != null) {conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}

		return cnt;
	}

	public ArrayList<AlbumsBean> align(String col, String way) {
		
		getConnection() ;
		//select * from albums order by song desc;
		String sql = "select * from albums order by " + col + " " +  way ;

		ArrayList<AlbumsBean> lists = new ArrayList<AlbumsBean>();

		try {
			ps = conn.prepareStatement(sql) ; 

			rs = ps.executeQuery() ;

			while(rs.next()){
				AlbumsBean album = new AlbumsBean() ;
				album.setNum( rs.getInt("num")) ;
				album.setSong( rs.getString("song") );
				album.setSinger( rs.getString("singer")) ; 
				album.setCompany( rs.getString("company") );
				album.setPrice( rs.getInt("price") ); 
				album.setPub_day( String.valueOf( rs.getDate("pub_day")));
				lists.add( album ) ;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}finally{
			try {
				if(rs != null) {rs.close(); }
				if(ps != null) {ps.close(); }
				if(conn != null) {conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		return lists ;
	}
}










