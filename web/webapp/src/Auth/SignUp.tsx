import React, {useEffect, useState} from 'react'
// import { useHistory } from 'react-router'
// import './signup.css'
// import {sign} from "jsonwebtoken"
// import {restfulApi} from "../api/api"
// import {AuthControllerApiAxiosParamCreator, AuthControllerApiFactory, AuthControllerApiFp} from "../api"
// import { AuthProvider } from './AuthProvider'
//
// //eslint:import { restfulApi } from "../api/api"; 이런식으로 약간 클리어 하게 만들어주는거다
//
// type SignUpInput = {
//   id: string
//   password: string
//   repassword: string
//   name: string
//   userGroup: string
//   serialNo: string
//   grade: string
//   phone: string
// }
//
// export const SignUp: React.FC = () => {
//   const [signUpInput, setSignUpInput] = useState<Partial<SignUpInput>>({})
//   const [checkResult, setCheckResult] = useState(0)
//   const [checkPassword, setCheckPassword] = useState(0)
//   const history = useHistory()
//
//   const handleSignUp = async () => {
//     try {
//       if (signUpInput.password !==null || signUpInput.password !== undefined && signUpInput.password === signUpInput.repassword) {
//         setCheckPassword(1)
//         alert('비밀번호 일치합니다.')
//         console.log('password success')
//         history.push('/signup')
//         const response = await AuthProvider.signUp(
//           signUpInput.id || '',
//           signUpInput.password || '',
//           signUpInput.repassword || '',
//           signUpInput.name || '',
//           signUpInput.userGroup || '',
//           signUpInput.serialNo || '',
//           signUpInput.grade || '',
//           // signUpInput.phone || '',
//         )
//         if (response) {
//           console.log('success')
//           alert('성공')
//           history.push('/login')
//         }
//       } else {
//         setCheckPassword(2)
//         alert('비밀번호가 일치 하지 않습니다.')
//         console.log('password fail')
//       }
//     } catch (e) {
//       console.log('Invalid column')
//     }
//   }
//
//   // const handleIdCheck = async () => {
//   //
//   //   let id : string | undefined
//   //
//   //   try{
//   //         // const res = await restfulApi.get<SignUpInput>('/auth/checkid', {
//   //         //         "id": signUpInput.id
//   //         //     }
//   //      const res =  AuthControllerApiFp().checkId(checkResult)
//   //           .then(res => {
//   //             if (res == 200) {
//   //                 setCheckResult(1)
//   //                 alert("사용 가능한 id입니다.");
//   //             }
//   //         }).catch(() => {
//   //                 setCheckResult(2)
//   //                 alert("사용 불가능한 id입니다.");
//   //             }
//   //         )
//   //     }catch (e) {
//   //         console.log(e);
//   //     }
//   // }
//
//
//   return (
//     <div className="signup_page">
//       <div className="main_title">지능형 배송로봇 관제시스템</div>
//       <div className="signup">
//         <div className="signup_screen">
//           <div className="signup_title">회원가입 정보를 입력하세요.</div>
//           <div className="signup_form">
//             <div className="control_group">
//               <label htmlFor="id">
//                 아이디
//                 <input
//                   type="text"
//                   className="signup_field"
//                   value={signUpInput.id || ''}
//                   placeholder="아이디를 입력해주세요."
//                   onChange={e => {
//                     const id = e.target.value
//                     setSignUpInput(old => ({ ...old, id }))
//                   }}
//                 />
//                   {checkResult == 0 ? (
//                       <span style={{ color: 'blue' }}></span>
//                   ) : checkResult == 1 ? (
//                       <span style={{ color: 'blue' }}> 사용 가능한 id입니다. </span>
//                   ) : (
//                       <span style={{ color: 'red' }}> 사용 불가능한 id입니다. </span>
//                   )}
//                 <div>
//                   <button className="btn" >
//                     중복 확인
//                   </button>
//                 </div>
//               </label>
//             </div>
//             <div className="control_group">
//               <label htmlFor="password">
//                 비밀번호
//                 <input
//                   type="password"
//                   className="signup_field"
//                   value={signUpInput.password || ''}
//                   placeholder="비밀번호를 입력해주세요."
//                   onChange={e => {
//                     const password = e.target.value
//                     setSignUpInput(old => ({ ...old, password }))
//                   }}
//                 />
//               </label>
//             </div>
//             <div className="control_group">
//               <label htmlFor="password">
//                 비밀번호 확인
//                 <input
//                   type="password"
//                   className="signup_field"
//                   value={signUpInput.repassword || ''}
//                   placeholder="비밀번호 확인을 입력해주세요."
//                   onChange={e => {
//                     const repassword = e.target.value
//                     setSignUpInput(old => ({ ...old, repassword }))
//                   }}
//                 />
//                 {checkPassword == 0 ? (
//                   <span style={{ color: 'blue' }}></span>
//                 ) : checkPassword == 1 ? (
//                   <span style={{ color: 'blue' }}> 비밀번호가 일치합니다.</span>
//                 ) : (
//                   <span style={{ color: 'red' }}> 비밀번호가 일치하지 않습니다.</span>
//                 )}
//               </label>
//             </div>
//             <div className="control_group">
//               <label htmlFor="name">
//                 이름
//                 <input
//                   type="name"
//                   className="signup_field"
//                   value={signUpInput.name || ''}
//                   placeholder="이름을 입력해주세요."
//                   onChange={e => {
//                     const name = e.target.value
//                     setSignUpInput(old => ({ ...old, name }))
//                   }}
//                 />
//               </label>
//             </div>
//             <div className="control_group">
//               <label htmlFor="userGroup">
//                 소속
//                 <input
//                   type="text"
//                   className="signup_field"
//                   value={signUpInput.userGroup || ''}
//                   placeholder="해당 소속을 입력해주세요."
//                   onChange={e => {
//                     const userGroup = e.target.value
//                     setSignUpInput(old => ({ ...old, userGroup }))
//                   }}
//                 />
//               </label>
//             </div>
//             <div className="control_group">
//               <label htmlFor="serialNo">
//                 군인 번호
//                 <input
//                   type="text"
//                   className="signup_field"
//                   value={signUpInput.serialNo || ''}
//                   placeholder="군번을 입력해주세요."
//                   onChange={e => {
//                     const serialNo = e.target.value
//                     setSignUpInput(old => ({ ...old, serialNo }))
//                   }}
//                 />
//               </label>
//             </div>
//             <div className="control_group">
//               <label htmlFor="grade">
//                 계급
//                 <input
//                   type="text"
//                   className="signup_field"
//                   value={signUpInput.grade || ''}
//                   placeholder="계급을 입력해주세요."
//                   onChange={e => {
//                     const grade = e.target.value
//                     setSignUpInput(old => ({ ...old, grade }))
//                   }}
//                 />
//               </label>
//             </div>
//             <div className="control_group">
//               <label htmlFor="phone">
//                 휴대전화
//                 <input
//                   type="text"
//                   className="signup_field"
//                   value={signUpInput.phone || ''}
//                   placeholder="휴대전화를 입력해주세요."
//                   onChange={e => {
//                     const phone = e.target.value
//                     setSignUpInput(old => ({ ...old, phone }))
//                   }}
//                 />
//               </label>
//             </div>
//             {/*<div className="control_group">*/}
//             {/*  <label htmlFor="role">*/}
//             {/*    사용유형*/}
//             {/*    <select*/}
//             {/*      name="role"*/}
//             {/*      value={signUpInput.role || ''}*/}
//             {/*      onChange={e => {*/}
//             {/*        const role = e.target.value*/}
//             {/*        setSignUpInput(old => ({ ...old, role }))*/}
//             {/*      }}>*/}
//             {/*      <option>select</option>*/}
//             {/*      <option value="A">Admin</option>*/}
//             {/*      <option value="N">Normal</option>*/}
//             {/*    </select>*/}
//             {/*  </label>*/}
//             {/*</div>*/}
//           </div>
//           <div>
//             <button className="btn" onClick={() => handleSignUp()}>
//               회원가입 등록
//             </button>
//           </div>
//         </div>
//         <div></div>
//       </div>
//     </div>
//   )
// }
