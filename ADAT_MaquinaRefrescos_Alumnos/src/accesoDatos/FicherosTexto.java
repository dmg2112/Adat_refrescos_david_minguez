package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis = new File("ADAT_MaquinaRefrescos_Alumnos/Ficheros/datos/dispensadores.txt"); // FicheroDispensadores
	File fDep = new File("ADAT_MaquinaRefrescos_Alumnos/Ficheros/datos/depositos.txt"); // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(fDep));
			String[] line = br.readLine().trim().split(";");
			
			while (line != null) {
				if (line != null) {
					depositosCreados.put(Integer.parseInt(line[1]),
							new Deposito(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2])));
					
					try {
						line = br.readLine().trim().split(";");
					} catch (NullPointerException e) {
						line = null;
					}

				}

			}
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fDis));
			String[] line = br.readLine().split(";");
			while (line != null) {
				if (line != null) {
					dispensadoresCreados.put(line[0],
							new Dispensador(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3])));

					try {
						line = br.readLine().split(";");
					} catch (NullPointerException e) {
						line = null;
					}
				}

			}
			br.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean todoOK = true;
		try {
			obtenerDepositos();

		} catch (Exception e) {
			todoOK = false;
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;
		try {
			obtenerDispensadores();

		} catch (Exception e) {
			todoOK = false;
		}

		return todoOK;
	}

} // Fin de la clase