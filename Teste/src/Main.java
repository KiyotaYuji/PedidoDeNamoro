import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class Main {
    static class DialogWrapper {
        JDialog dialog;

        public DialogWrapper(JDialog dialog) {
            this.dialog = dialog;
        }
    }

    public static void main(String[] args) {
        Object[] options = {"Sim", "Não"};
        Random random = new Random();

        JOptionPane optionPane = new JOptionPane("Você quer namorar comigo?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, options[0]);
        DialogWrapper dialogWrapper = new DialogWrapper(optionPane.createDialog("Pergunta importante"));
        dialogWrapper.dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        dialogWrapper.dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Move the dialog to a random position on the screen when it is closed
                int newX = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width - dialogWrapper.dialog.getWidth());
                int newY = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().height - dialogWrapper.dialog.getHeight());
                dialogWrapper.dialog.setLocation(newX, newY);
            }
        });

        while (true) {
            dialogWrapper.dialog.setVisible(true);

            Object selectedValue = optionPane.getValue();
            if (selectedValue != null) {
                if (selectedValue.equals("Sim")) {
                    JOptionPane.showMessageDialog(null, "Que bom! Estou muito feliz!");
                    break;
                } else if (selectedValue.equals("Não")) {
                    dialogWrapper.dialog.dispose(); // Close the dialog when "Não" is clicked
                    // Move the dialog to a random position on the screen when "Não" is clicked
                    int newX = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width - dialogWrapper.dialog.getWidth());
                    int newY = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().height - dialogWrapper.dialog.getHeight());
                    dialogWrapper.dialog = optionPane.createDialog("Pergunta importante"); // Create a new dialog at the new position
                    dialogWrapper.dialog.setLocation(newX, newY);
                    dialogWrapper.dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    dialogWrapper.dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            // Move the dialog to a random position on the screen when it is closed
                            int newX = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width - dialogWrapper.dialog.getWidth());
                            int newY = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().height - dialogWrapper.dialog.getHeight());
                            dialogWrapper.dialog.setLocation(newX, newY);
                        }
                    });
                }
            }
        }
    }
}