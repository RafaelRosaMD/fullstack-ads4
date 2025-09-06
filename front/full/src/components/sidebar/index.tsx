

function Sidebar(){
    return(

        <div>
            <div>
                <img src="" alt="logo">
                </img>
            </div>
            <ul>
                <li>
                    <a href="">Home</a>
                </li>
                <li>
                    <a href="">Cadastro</a>
                    <ul className="collapse" id="submenucadastro"></ul>
                    <ul>
                        <li>
                            <a href="/usuario">Usuário</a>
                        </li>
                        <li>
                            <a href="/carrinho">Carrinho</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        
        
        
    )
}

export default Sidebar;