using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace interfaceBD
{
    class Musico
    {
		private String _numCC;
		private String _nome;
		private String _nomeArt;
		private String _email;
		private String _sexo;
		private String _banda;

        public string NumCC { get => _numCC; set => _numCC = value; }
        public string Nome { get => _nome; set => _nome = value; }
        public string NomeArt { get => _nomeArt; set => _nomeArt = value; }
        public string Email { get => _email; set => _email = value; }
        public string Sexo { get => _sexo; set => _sexo = value; }
        public string Banda { get => _banda; set => _banda = value; }

        public override String ToString()
        {
            return String.Format("{0, -12}{1, -36}{2, -26}{3, -30}{4, -6}{5, -25}", NumCC, Nome, NomeArt, Email, Sexo, Banda);
        }

        public static String Fline()
        {
            return String.Format("{0, -12}{1, -36}{2, -26}{3, -30}{4, -6}{5, -25}", "CC", "Nome", "Nome Artistico", "Email", "Sexo", "Banda");
        }
    }
}
