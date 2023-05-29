Aplicativo para Android 6 ou superior, com as seguintes caracteristicas:

- Linguagem : Kotlin;	
- Arquitetura: MVVM;	
- Layout responsivo;
- Uso de RecyclerViews e celulas personalizadas para exibição de conteudos;
- Testes unitarios para as requisições e uso da conversão de datas para texto;
- Bibliotecas utilizadas:
	-Glide : Carregamento de imagens via URL
		link: https://github.com/bumptech/glide
	-Retrofit2 : Consumo de APIs rest
		link: https://github.com/square/retrofit
	-Okhtto: Auxiliar no uso do retrofit2 para capturar as requisições no console
		link: https://github.com/square/retrofit

Projeto já exportado para rodar diretamente no Android Studio, somente sendo necessário incluir uma chave para ter acesso a API do GitHub.
Para inlcuir essa API, vá na classe ServiceLinks e coloque a chave na variavel token