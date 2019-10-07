package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties(
					"ADAT_MaquinaRefrescos_Alumnos/Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/adat_maquinarefrescos_jdbc", "root", "");
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			// System.out.println("ERROR: No se ha podido conectar con la base de datos");
			// System.out.println(e.getMessage());
			e.printStackTrace();
			// System.out.println("No se ha podido inicializar la maquina\n Finaliza la
			// ejecucion");
			// System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> temp = new HashMap<Integer, Deposito>();
		try {
			PreparedStatement pstmt = conn1.prepareStatement("select * from depositos");
			ResultSet rset = pstmt.executeQuery();

			while (rset.next()) {
				temp.put(rset.getInt("valor"),
						new Deposito(rset.getString("nombre"), rset.getInt("valor"), rset.getInt("cantidad")));

			}
			rset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return temp;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> temp = new HashMap<String, Dispensador>();
		try {
			PreparedStatement pstmt = conn1.prepareStatement("select * from Dispensadores");
			ResultSet rset = pstmt.executeQuery();

			while (rset.next()) {
				temp.put(rset.getString("Clave"), new Dispensador(rset.getString("clave"), rset.getString("nombre"),
						rset.getInt("precio"), rset.getInt("cantidad")));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return temp;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		for (Entry<Integer, Deposito> entry : depositos.entrySet()) {

			Deposito dep = entry.getValue();
			Integer llave = entry.getKey();
			try {
				PreparedStatement pstmt = conn1.prepareStatement("Update depositos set cantidad = ? where valor = ? ");
				pstmt.setInt(1, dep.getCantidad());
				pstmt.setInt(2, llave);
				pstmt.executeUpdate();
				pstmt.close();

			} catch (SQLException e) {
				todoOK = false;
				e.printStackTrace();
			}

		}

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		for (Entry<String, Dispensador> entry : dispensadores.entrySet()) {

			Dispensador dep = entry.getValue();
			String llave = entry.getKey();
			try {
				PreparedStatement pstmt = conn1.prepareStatement("Update dispensadores set cantidad = ? where clave = ? ");
				pstmt.setInt(1, dep.getCantidad());
				pstmt.setString(2, llave);
				pstmt.executeUpdate();
				pstmt.close();

			} catch (SQLException e) {
				todoOK = false;
				e.printStackTrace();
			}

		}

		return todoOK;
	}

} // Fin de la clase