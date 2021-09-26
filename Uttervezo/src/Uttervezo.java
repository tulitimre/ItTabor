
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tulit Imre
 */
public class Uttervezo {

    int[] koltsegek = new int[51];
    int[][] tavolsag = new int[51][51];
    boolean elso = true;
    boolean[] voltItt = new boolean[51];
    int eredmenyIndex = 0;
    boolean kezdopont = true;

    public Uttervezo(String allomanyNev) {
        try {
            File adatok = new File(allomanyNev);
            int koltsegIndex = 0;
            Scanner myReader = new Scanner(adatok);
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] adatokKulon = data.trim().split(" ");
                if (elso) {
                    for (int i = 0; i < 51; i++) {
                        koltsegek[i] = Integer.parseInt(adatokKulon[i]);
                    }
                    elso = false;
                } else {

                    for (int j = 0; j < 51; j++) {
                        tavolsag[koltsegIndex][j] = Integer.parseInt(adatokKulon[j]);
                    }
                    koltsegIndex++;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void Tervez(int kiidulPont, int celPont, int uzemanyagMennyiseg, int osszPenz) {

        for (int i = 0; i < 51; i++) {
            if (tavolsag[kiidulPont][i] != 0) {
                uzemanyagMennyiseg -= tavolsag[kiidulPont][i];
                osszPenz -= koltsegek[i];
                megTervez(kiidulPont, i, uzemanyagMennyiseg, osszPenz, celPont, voltItt);
            }
        }

    }

    public void megTervez(int kapottKiindulopont, int jelenlegiPont, int maradtUzemanyag, int maradtPenz, int celPont, boolean[] latogatott) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("eredmeny.txt", true));
            latogatott[kapottKiindulopont] = true;
            latogatott[jelenlegiPont] = true;

            if (jelenlegiPont != celPont) {
                for (int i = 0; i < 51; i++) {
                    if (tavolsag[jelenlegiPont][i] != 0 && !latogatott[i] && (maradtUzemanyag - tavolsag[jelenlegiPont][i]) >= 0 && (maradtPenz - koltsegek[i]) >= 0) {
                        maradtUzemanyag -= tavolsag[jelenlegiPont][i];
                        maradtPenz -= koltsegek[i];
                        eredmenyIndex++;
                        writer.append(i + " → ");
                        megTervez(jelenlegiPont, i, maradtUzemanyag, maradtPenz, celPont, latogatott);
                    }
                }
            }
            writer.append("\n");
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Uttervezo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
