import tkinter as tk
from tkinter import messagebox
import random
import json # <- NOVO: Para formatar nossa mensagem
import urllib.request # <- NOVO: Para enviar a mensagem pela internet
import threading # <- NOVO: Para não travar o programa enquanto envia

def send_notification():
    """Envia a notificação para o Discord sem travar a interface."""
    try:
        # IMPORTANTE: Cole a URL do seu Webhook aqui dentro das aspas!
        webhook_url = "Não nasci ontem" 

        # A mensagem que você quer enviar
        message = {
            "content": "💖 ALERTA: Alguém acabou de clicar em 'Sim' no seu programa! 💖"
        }

        # Prepara a requisição
        data = json.dumps(message).encode('utf-8')
        req = urllib.request.Request(webhook_url, data=data, headers={'Content-Type': 'application/json'})
        
        # Envia a mensagem
        urllib.request.urlopen(req)
        print("Notificação enviada!") # Isso aparecerá no console se você rodar como script
    except Exception as e:
        print(f"Erro ao enviar notificação: {e}") # Caso não tenha internet

def move_window(window):
    """Move a janela para uma posição aleatória na tela."""
    screen_width = window.winfo_screenwidth()
    screen_height = window.winfo_screenheight()
    window_width, window_height = 300, 100

    new_x = random.randint(0, screen_width - window_width)
    new_y = random.randint(0, screen_height - window_height)
    window.geometry(f"+{new_x}+{new_y}")

def on_yes_click():
    """Executado quando o botão 'Sim' é clicado."""
    messagebox.showinfo("Resposta!", "Que bom! Estou muito feliz!")
    
    # NOVO: Chama a função de notificação em uma thread separada
    # para que o programa não espere a internet responder.
    threading.Thread(target=send_notification).start()
    
    root.destroy()

def on_no_click():
    """Executado quando o botão 'Não' é clicado."""
    move_window(root)

def on_closing():
    """Executado quando o usuário tenta fechar a janela pelo 'X'."""
    move_window(root)

# Configuração da janela principal
root = tk.Tk()
root.title("Pergunta importante")
root.resizable(False, False)
root.eval('tk::PlaceWindow . center')

# Itens da janela (Label, Botões)
label = tk.Label(root, text="Você quer namorar comigo?", font=("Arial", 14), padx=20, pady=20)
label.pack()
button_frame = tk.Frame(root, pady=10)
button_frame.pack()
yes_button = tk.Button(button_frame, text="Sim", command=on_yes_click, width=10)
yes_button.pack(side=tk.LEFT, padx=10)
no_button = tk.Button(button_frame, text="Não", command=on_no_click, width=10)
no_button.pack(side=tk.LEFT, padx=10)

# Intercepta o evento de fechar a janela
root.protocol("WM_DELETE_WINDOW", on_closing)

# Inicia o loop da aplicação

root.mainloop()
