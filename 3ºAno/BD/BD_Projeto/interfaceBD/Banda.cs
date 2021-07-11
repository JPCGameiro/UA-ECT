using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace interfaceBD
{
    class Banda
    {
		private String _id;
		private String _nome;
		private String _telefone;
		private String _email;
		private String _numElem;
		private String _genero;

        public string Id { get => _id; set => _id = value; }
        public string Nome { get => _nome; set => _nome = value; }
        public string Telefone { get => _telefone; set => _telefone = value; }
        public string Email { get => _email; set => _email = value; }
        public string NumElem { get => _numElem; set => _numElem = value; }
        public string Genero { get => _genero; set => _genero = value; }

        public override String ToString()
        {
            return String.Format("{0, -7}{1, -35}{2, -16}{3, -30}{4, -9}{5, -15}", Id, Nome, Telefone, Email, NumElem, Genero);
        }

        public static String Fline()
        {
            return String.Format("{0, -7}{1, -35}{2, -16}{3, -30}{4, -9}{5, -15}", "Id", "Nome", "Telefone", "Email", "n. Elem", "Genero");
        }
    }
}
