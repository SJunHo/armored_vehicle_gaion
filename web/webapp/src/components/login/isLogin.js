
const isLogin = () => localStorage.getItem("user") === null ? false :
                    (JSON.parse(localStorage.getItem("user")).roles[0] === "ROLE_ADMIN" ? true :
                        (JSON.parse(localStorage.getItem("user")).roles[0] === "ROLE_MODERATOR" ? true : false) )
                        
export default isLogin;
