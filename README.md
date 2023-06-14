# Opis aplikacji

Aplikacja "30 Seconds!" została stworzona na system Android i służy do nauki języków. Użytkownicy mają możliwość wyboru jednego z pięciu obecnie dostępnych języków, takich jak polski, angielski, niemiecki, włoski czy hiszpański. W ramach gry zostały utworzone cztery różne typy zadań, z którymi użytkownik musi się zmierzyć. Aplikacja posiada przyjemny dla oka oraz wygodny w użytkowaniu interfejs.

# Opis technologii

Do stworzenia aplikacji wykorzystano następujące technologie:

- Java
- PHP
- MySQL
- SQLite

Aplikacja korzysta z API do pobierania i modyfikowania danych z bazy danych. Jednak lokalnie tworzona jest również baza danych SQLite, która stanowi lustrzane odbicie bazy danych z API. Taka decyzja została podjęta w celu minimalizacji obciążenia serwera oraz optymalizacji działania aplikacji.

# Architektura aplikacji i opis funkcjonalności

## API

Aplikacja wysyła zapytanie do serwera, który następnie przetwarza żądanie i w zależności od rezultatu wysyła informację zwrotną do klienta w formacie JSON. Następnie odebrane dane są przetwarzane w aplikacji klienta.

## Activities

Architektura aplikacji bazuje na trzech głównych aktywnościach, której każdej z nich przypadają oddzielne fragmenty.

###	Main
Jest to startowa aktywności, która korzysta z dwóch fragmentów służących do logowania lub rejestrowania.
W przypadku logowania jak i rejestracji do serwera zostaje wysyłane zapytanie z danymi użytkownika metodą POST, które w celu bezpieczeństwa zostały zahashowane algorytmem SHA-1. Jeżeli odpowiedź będzie pozytywna nastąpi przejście do następnej Aktywności. 
[![login/register](https://i.ibb.co/F3HydwQ/login-register.png)](https://ibb.co/ZX2Fb8Q)

### LoggedUser
Dla tej Aktywności domyślnie ładowanym fragmentem jest HomePage, który wygląda następująco:
Dodatkowo w tej Aktywności zostają pobierane dane z Api a dokładniej przykłady wykorzystywane do gry, jednak nie są one pobierane przy każdym uruchomieniu aplikacji. Istnieją dwie możliwości w których dane zostają pobierane – użytkownik nie logował się do aplikacji dłużej niż 3 dni lub jest to pierwsze uruchomienie aplikacji. Dane te przechowywane są w pliku konfiguracyjnym aplikacji.
Do tej aktywności został przypisany dolny pasek nawigacyjny który umożliwia przechodzenie pomiędzy wybranymi fragmentami.
[![startPage](https://i.ibb.co/XZLS0g3/home.png)](https://ibb.co/1fQ8y5d)

Kolejnym fragmentem, który jest przypisany do tej Aktywności jest FragmentLeaderBoard, który wyświetla w ScrollView ranking graczy.
[![leaderboard](https://i.ibb.co/GCtfC5m/leaderboard.png)](https://ibb.co/VMQ0Mq6)

Ostatnim fragmentem dla tej aktywności jest FragmentSettings.
Służy on do wybrania przez użytkownika języka którego chce się nauczyć oraz jego języka ojczystego. Dane o wybranych przez użytkownika językach są zapisywane do pliku konfiguracyjnego. 
[![settings](https://i.ibb.co/qRTLQfP/settings-both.png)](https://ibb.co/XzcfRgv)

### Game
Ostatnia Aktywność odpowiada za obsłużenie gry. Rdzeniem ten aktywności jest losowanie a następnie ładowanie fragmentów będącymi różnymi typami zadań. 
[![Game fragments](https://i.ibb.co/bXqfG3Z/gamefragment.png)](https://ibb.co

