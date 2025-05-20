/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Components;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author dary_
 */
public class PanelLogin extends JPanel{
    
    public PanelLogin() {
        setLayout(new MigLayout("fillx,wrap,insets 30 40 50 40, width 320", "[fill]", "[]20[][]15[][]30[]"));
        putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "arc:20;");
    }
    
}
