$(function() {
    var e = $("#home"); !
    function() {
        var i, t, r, a, s, n = e.find(".slider"),
        o = n.find(".slider-list"),
        l = n.find(".dot-list"),
        c = o.find("a"),
        d = 0,
        u = !0,
        h = null,
        g = 5,
        f = o.children("div").length;

        for (c.bind("click",
        function() {
            var e = $(this).closest("div").index() + 1;
            tlog = window.tlog || [],
            tlog.push("c:w_index_adframe_" + e)
        }), o.find("div:first").show().siblings().hide(), i = function(e, i) {
            u && (u = !1, l.find("a").eq(e).addClass("active").siblings().removeClass("active"), o.find("div").eq(e).siblings().fadeOut(600).end().fadeIn(1200,
            function() {
                u = !0,
                i && i.call(this)
            }))
        },
        t = function() {
            u && (d++, d >= f && (d = 0), i(d), clearTimeout(h), h = setTimeout(t, 1e3 * g))
        },
        r = function() {
            h = setTimeout(t, 1e3 * g)
        },
        a = "", s = 0; f > s; s++) a += '<a href="javascript:;"',
        0 == s && (a += ' class="active"'),
        a += "></a>";
        l.html(a).find("a").click(function() {
            var e = $(this).index();
            d = e,
            clearTimeout(h),
            i(e,
            function() {
                r()
            })
        }),
        r()
    } (),
    LT.File.Js.load("http://core.pc.lietou-static.com/revs/js/common/plugins/jquery.placeholderui_4ea8db00.js,http://core.pc.lietou-static.com/revs/js/common/plugins/jquery.checkboxui_1589ca87.js",
    function() {
        $("[placeholder]").PlaceholderUI(),
        $('input[type="checkbox"]').CheckboxUI();
        var i = function() {
            this.tab = $(".form-title", e),
            this.con = this.tab.siblings(".form-content");
            var i = parseInt(this.con.width(), 10) + 2;
            this.sendGA = function() {
                var e = this.con.attr("data-flag"),
                i = this.tab.find(">.cur").attr("data-kind");
                if (0 == e) switch (Number(i)) {
                case 0:
                    tlog = window.tlog || [],
                    tlog.push("s:w_box_register_c_new");
                    break;
                case 1:
                    tlog = window.tlog || [],
                    tlog.push("s:w_box_register_e_new");
                    break;
                case 2:
                    tlog = window.tlog || [],
                    tlog.push("s:w_box_register_h_new")
                } else switch (Number(i)) {
                case 0:
                    tlog = window.tlog || [],
                    tlog.push("s:w_box_login_c_new");
                    break;
                case 1:
                    tlog = window.tlog || [],
                    tlog.push("s:w_box_login_e_new");
                    break;
                case 2:
                    tlog = window.tlog || [],
                    tlog.push("s:w_box_login_h_new")
                }
            },
            this.tabSwitch = function(e) {
                var t = ".register-box",
                r = -i,
                a = this.con.attr("data-flag");
                1 == a ? (t = ".login-box", r = i) : (t = ".register-box", r = -i),
                this.tab.find("span").eq(e).addClass("cur").siblings().removeClass("cur"),
                "0" == this.tab.find("span").eq(e).attr("data-kind") ? this.con.children("div").eq(e).children(t).css("left", "0").show().siblings().css("left", r + "px").hide() : this.con.children("div").eq(e).children(".login-box").css("left", "0"),
                this.con.children("div").eq(e).css("left", "0").siblings().css("left", "-584px"),
                this.sendGA()
            },
            this.showWhich = function() {
                var e, i, t = 0;
                this.con.attr("data-flag", LT.User.user_login ? 1 : 0),
                this.tab.find('span[data-kind="' + t + '"]').trigger("click"),
                e = LT.String.getQuery("sr_id"),
                i = LT.String.getQuery("sr_email"),
                !t && !LT.User.user_login && e && i && $(".candidate .login-box").attr("sr_id", e).find('[data-selector="checkEmail"]').val(i)
            },
            this.lrswitch = function(e) {
                var t = 0,
                r = 0;
                1 == $(e).hasClass("login-box") ? (r = 0, t = -i) : (r = 1, t = i),
                $(e).animate({
                    left: t
                },
                200).hide().siblings("form").animate({
                    left: 0
                },
                200).show().find(".placeholderui").show(),
                $(e).closest(".form-content").attr("data-flag", r),
                this.sendGA()
            },
            this.init = function() {
                var e = this;
                e.tab.find("span").bind("click",
                		function() {
                    	e.tabSwitch($(this).index())
                }),
                $('[data-selector="switchRegister"],[data-selector="switchLogin"]').bind("click",
                function() {
                    e.lrswitch($(this).closest("form"))
                }),
                e.showWhich()
            }
        }; (new i).init()
    }),
    LT.File.Js.load("http://s.lietou-static.com/revs/p/beta2/js/plugins/jquery.validTip_3908ed67.js,http://core.pc.lietou-static.com/revs/js/common/plugins/jquery.valid_cbce39ff.js",
    function() {
        function i(e, i) {
            if (!e) return ! 1;
            var e = e,
            t = i || 60,
            r = null;
            e.removeClass("btn-primary").addClass("btn-disabled"),
            function() {
                return t--,
                e.html("重新获取（" + t + "）"),
                0 >= t ? (e.removeClass("btn-disabled").addClass("btn-primary").html("重新获取"), clearTimeout(r), !1) : void(r = setTimeout(arguments.callee, 1e3))
            } ()
        }
        var t, r, a = $(".candidate").find(".login-box"),
        s = $(".candidate").find(".register-box"),
        n = $(".hunter").find(".login-box"),
        o = $(".hunter").find(".register-box"),
        l = $(".business").find(".login-box"),
        c = $(".business").find(".register-box"),
        d = $('[data-selector="phone-code-wrap"]', s),
        u = $('[data-selector="email-code-wrap"]', s),
        h = $('[data-selector="phone-code-btn"]', d).data("times", 0),
        g = $('[data-selector="checkEmail"]', s);
        h.bind("click",
        function() {
            if ($(this).hasClass("btn-disabled")) return ! 1;
            if (!g.attr("data-valid") || "false" == g.attr("data-valid")) return g.trigger("highlight", !0).trigger("focus"),
            !1;
            var e = $(this),
            t = e.data("times") - 0;
            return 1 == LT.Cookie.get("phone_code_times") ? (i(e), !1) : t >= 3 ? (e.data("times", 0), LT.Cookie.set("phone_code_times", 1, 1 / 24 / 2), i(e), g.SimpleValidErrorTips("如果您一直无法收到验证码，请用邮箱注册"), !1) : void $.ajax({
                url: LT.Env.wwwRoot + "user/sendverifymessage?__mn__=newtel",
                type: "POST",
                data: "newtel=" + $.trim(g.val()),
                dataType: "json",
                success: function(r) {
                    1 == r.flag ? (t++, e.data("times", t), i(e)) : $.dialog.error(r.msg)
                },
                error: function() {
                    $.dialog.error(data.msg)
                }
            })
        }),
        t = $(".changecode", e),
        r = t.siblings(".validcode"),
        t.add(r).bind("click",
        function() {
            return $(this).closest("form").find(".validcode").attr("src", "/image/randomcode/?" + Math.random()),
            !1
        }),
        a.valid({
            scan: function(e) {
                e.valid ? a.find(".text-error").removeClass("text-error") : ($.each(e.result,
                function(e, i) { ! i.valid && i.element.trigger("highlight", !0)
                }), e.firstError.element.triggerHandler("focus"))
            },
            success: function() {
                var e, i = $(this),
                t = LT.String.getQuery("url") && LT.String.getQuery("url").indexOf("liepin.com") > -1 ? LT.String.getQuery("url") : !1,
                r = $("input[name='user_login']", i),
                a = $("input[name='user_pwd']", i),
                s = a.val();
                return a.val(LT.String.md5(s)),
                e = i.serializeArray(),
                a.val(s),
                $.ajax({
                    url: i.attr("action"),
                    type: i.attr("method"),
                    data: e,
                    dataType: "json",
                    cache: !1,
                    success: function(e) {
                        switch (parseInt(e.flag)) {
                        case - 1 : -1 != e.err.indexOf("用户名") ? r.SimpleValidErrorTips(e.err) : -1 != e.err.indexOf("密码") ? a.SimpleValidErrorTips(e.err) : r.SimpleValidErrorTips(e.err);
                            break;
                        case 1:
                            location.href = t ? t: LT.Env.wwwRoot + "home/";
                            break;
                        case 2:
                            top.location.href = "/user/verificationfail/?user_email=" + e.user_email;
                            break;
                        case 3:
                            top.location.href = "/user/regc/regprofile/?user_email=" + e.user_email;
                            break;
                        case 4:
                            top.location.href = "/user/regc/regnamecard/?user_email=" + e.user_email;
                            break;
                        case 5:
                            top.location.href = "/user/verificationfail/?user_email=" + e.user_email;
                            break;
                        case 0:
                            r.SimpleValidErrorTips("您的账号已被禁用！");
                            break;
                        default:
                            $.dialog.alert("发生未知错误，请与系统管理员联系，错误代码：(INDEX:LOGIN_" + e.flag + ")")
                        }
                    },
                    mask: $(":submit", i)
                }),
                !1
            }
        }),
        s.valid({
            scan: function(e) {
                e.valid ? s.find(".text-error").removeClass("text-error") : ($.each(e.result,
                function(e, i) { ! i.valid && i.element.trigger("highlight", !0)
                }), e.firstError.element.triggerHandler("focus"))
            },
            dynrule: {
                checkPhoneEmail: function() {
                    return LT.String.isEmail($(this).val()) ? (u.show().find("input").prop("disabled", !1), d.hide().find("input").prop("disabled", !0), [["email", "请输入正确的$"], ["ajax", "checkMailExist", "该邮箱已注册，请更换邮箱"]]) : LT.String.isMobile($(this).val()) ? (u.hide().find("input").prop("disabled", !0), d.show().find("input").prop("disabled", !1), [["phone", "请输入正确的$"]]) : [["phone", "请输入正确的$"], ["email", "请输入正确的$"]]
                }
            },
            ajax: {
                checkMailExist: {
                    url: LT.Env.wwwRoot + "user/isuserexist/",
                    data: function() {
                        return {
                            user_login: $('[data-selector="checkEmail"]', s).val()
                        }
                    },
                    dataType: "json",
                    cache: !1,
                    success: function(e) {
                        return 1 == e ? ($('[data-selector="checkEmail"]', s).trigger("highlight", !0), !1) : !0
                    }
                }
            },
            success: function() {
                var e = $(this),
                i = $('[name="web_user.user_pwd"]', s),
                t = i.val();
                return /\b(000000|111111|11111111|112233|123123|123321|123456|12345678|654321|666666|888888|1234567)\b/.test(t) ? (i.SimpleValidErrorTips("密码安全度低，请更换密码"), !1) : /"|'|<|>|\?|\%|\*/g.test(t) ? (i.SimpleValidErrorTips("请使用常用符号"), !1) : ($.ajax({
                    url: e.attr("action"),
                    type: e.attr("method"),
                    data: e.serializeArray(),
                    dataType: "json",
                    cache: !1,
                    success: function(i) {
                        if (i.success) {
                            var t = "";
                            s.attr("sr_id") && (t = "?sr_id=" + s.attr("sr_id")),
                            window.location.href = "/user/regc/regnamecard/" + t
                        } else $("img.validcode", e).trigger("click"),
                        i.msg.indexOf("验证码") >= 0 ? $('input[name="rand"]', e).filter(function() {
                            return this.disabled ? void 0 : $(this)
                        }).SimpleValidErrorTips(i.msg) : $('[data-selector="checkEmail"]', e).SimpleValidErrorTips(i.msg)
                    },
                    mask: $(":submit", e)
                }), !1)
            }
        }),
        n.valid({
            scan: function(e) {
                e.valid ? n.find(".text-error").removeClass("text-error") : ($.each(e.result,
                function(e, i) { ! i.valid && i.element.trigger("highlight", !0)
                }), e.firstError.element.triggerHandler("focus"))
            },
            success: function() {
                var e, i = $(this),
                t = LT.String.getQuery("url") && LT.String.getQuery("url").indexOf("liepin.com") > -1 ? LT.String.getQuery("url") : !1,
                r = $("input[name='user_pwd']", i),
                a = r.val();
                return r.val(LT.String.md5(a)),
                e = i.serializeArray(),
                r.val(a),
                $.ajax({
                    url: i.attr("action"),
                    type: i.attr("method"),
                    data: e,
                    dataType: "json",
                    cache: !1,
                    success: function(e) { - 1 != e.flag ? location.href = t ? t: LT.Env.wwwRoot + "/home/": n.find('[name="user_login"]').SimpleValidErrorTips(e.err)
                    },
                    mask: $(":submit", i)
                }),
                !1
            }
        }),
        o.valid({
            scan: function(e) {
                e.valid ? o.find(".text-error").removeClass("text-error") : ($.each(e.result,
                function(e, i) { ! i.valid && i.element.trigger("highlight", !0)
                }), e.firstError.element.triggerHandler("focus"))
            },
            ajax: {
                checkMailExist: {
                    url: LT.Env.wwwRoot + "user/isuserexist/",
                    data: function() {
                        return {
                            user_login: o.find('input[name="user_login"]').val()
                        }
                    },
                    dataType: "json",
                    cache: !1,
                    success: function(e) {
                        return 1 == e ? (o.find('input[name="user_login"]').trigger("highlight", !0), !1) : !0
                    }
                },
                checkMobileExist: {
                    url: LT.Env.wwwRoot + "user/isuserhtelexist/",
                    data: function() {
                        return {
                            h_tel: o.find('input[name="h_tel"]').val()
                        }
                    },
                    dataType: "json",
                    cache: !1,
                    success: function(e) {
                        return 1 == e ? (o.find('input[name="h_tel"]').trigger("highlight", !0), !1) : !0
                    }
                }
            },
            success: function() {
                var e = $(this);
                return $.ajax({
                    url: e.attr("action"),
                    type: e.attr("method"),
                    data: e.serializeArray(),
                    dataType: "json",
                    cache: !1,
                    success: function(i) {
                        i.success ? window.location.href = LT.Env.hRoot + "certification/home/?register=1&" + Math.random() : -1 != i.msg.indexOf("邮箱") ? $('[name="user_login"]', e).SimpleValidErrorTips(i.msg) : -1 != i.msg.indexOf("手机") ? $('[name="h_tel"]', e).SimpleValidErrorTips(i.msg) : $.dialog.error(i.msg)
                    },
                    mask: $(":submit", e)
                }),
                !1
            }
        }),
        l.valid({
            scan: function(e) {
                e.valid ? l.find(".text-error").removeClass("text-error") : ($.each(e.result,
                function(e, i) { ! i.valid && i.element.trigger("highlight", !0)
                }), e.firstError.element.triggerHandler("focus"))
            },
            success: function() {
                var e, i = $(this),
                t = LT.String.getQuery("url") && LT.String.getQuery("url").indexOf("liepin.com") > -1 ? LT.String.getQuery("url") : !1,
                r = $("input[name='user_pwd']", i),
                a = r.val();
                return r.val(LT.String.md5(a)),
                e = i.serializeArray(),
                r.val(a),
                $.ajax({
                    url: i.attr("action"),
                    type: i.attr("method"),
                    data: e,
                    dataType: "json",
                    cache: !1,
                    success: function(e) {
                        switch (parseInt(e.flag)) {
                        case - 1 : -1 != e.err.indexOf("用户名") ? $('[name="user_login"]', i).SimpleValidErrorTips(e.err) : $.dialog.frize(e.err);
                            break;
                        case 1:
                            location.href = t ? t: LT.Env.wwwRoot + "/home/";
                            break;
                        case 2:
                            location.href = LT.Env.wwwRoot + "/user/verificationfail/?user_email=" + e.user_email;
                            break;
                        case 3:
                            location.href = LT.Env.wwwRoot + "/user/regc/regprofile/?user_email=" + e.user_email;
                            break;
                        case 4:
                            location.href = LT.Env.wwwRoot + "/user/regc/regnamecard/?user_email=" + e.user_email;
                            break;
                        case 5:
                            location.href = LT.Env.wwwRoot + "/user/verificationfail/?user_email=" + e.user_email;
                            break;
                        case 0:
                            l.find('[name="user_login"]').SimpleValidErrorTips("您的账号已被禁用！");
                            break;
                        default:
                            $.dialog.error("发生未知错误，请与系统管理员联系，错误代码：(INDEX:LOGIN_" + e.flag + ")")
                        }
                    },
                    mask: $(":submit", i)
                }),
                !1
            }
        }),
        c.valid({
            scan: function(e) {
                e.valid ? c.find(".text-error").removeClass("text-error") : ($.each(e.result,
                function(e, i) { ! i.valid && i.element.trigger("highlight", !0)
                }), e.firstError.element.triggerHandler("focus"))
            },
            ajax: {
                checkUserNameExist: {
                    url: LT.Env.wwwRoot + "user/checkEUserloginExist/",
                    data: {
                        user_login: c.find('input[name="web_user.user_login"]').val()
                    },
                    dataType: "json",
                    cache: !1,
                    success: function(e) {
                        return 1 == e.flag ? (c.find('input[name="web_user.user_login"]').trigger("highlight", !0), !1) : !0
                    }
                }
            },
            success: function() {
                var e = $(this);
                return $.ajax({
                    url: e.attr("action"),
                    type: e.attr("method"),
                    data: e.serializeArray(),
                    dataType: "json",
                    cache: !1,
                    success: function(i) {
                        i.success ? $.dialog.frize(i.msg,
                        function() {
                            location.href = LT.Env.lptRoot + "?reg=1&uname=" + $.trim($('input[name="web_user.user_login"]', e).val())
                        }).time(3) : -1 != i.msg.indexOf("验证码") ? ($(".validcode", e).attr("src", LT.Env.wwwRoot + "image/randomcode/?" + Math.random()), $('input[name="rand"]', e).SimpleValidErrorTips(i.msg)) : -1 != i.msg.indexOf("用户") ? $('input[name="web_user.user_login"]', e).SimpleValidErrorTips(i.msg) : $.dialog.error(i.msg)
                    },
                    mask: $(":submit", e)
                }),
                !1
            }
        }),
        $("[validate-rules]", e).SimpleValidTips()
    })
}),
$(function() {
    function e() {
        var e, i;
        LT.Browser.IE6 || (e = '<div id="scrollBar" class="scroll-bar"><ul></ul><a class="back-top" href="#"><i></i><b></b><span class="hide">回到顶部</span></a></div>', this.tplMain = $(e), i = [], $('h2[data-selecter="scroll"]').each(function() {
            i.push({
                id: $(this).attr("id"),
                name: $(this).attr("data-name")
            })
        }), this.init(i))
    }
    e.prototype = {
        init: function(e) {
            var i = this,
            t = {},
            r = [],
            a = [],
            e = e || [];
            e.forEach(function(e) {
                i.tplMain.find("ul").append('<li><a id="' + e.id + '-btn" href="#' + e.id + '">' + e.name + "</a></li>"),
                t[e.id] = $("#" + e.id).offset().top,
                r.push(t[e.id])
            }),
            r = r.sort(function(e, i) {
                return parseInt(e, 10) > parseInt(i, 10)
            }),
            r.forEach(function(e, i) {
                for (var r in t) if (e == t[r]) {
                    a[i] = r;
                    break
                }
            }),
            i.tplMain.appendTo("body"),
            i.opts = t,
            i.valArray = r,
            i.idArray = a,
            i.lastPostion = $("#" + i.idArray[i.idArray.length - 1]).parent().offset().top + $("#" + i.idArray[i.idArray.length - 1]).parent().outerHeight() - 10,
            i.nowId = "",
            i.lock = !1,
            i.bind()
        },
        bind: function() {
            var e = this;
            $(window).bind("load resize scroll",
            function(i) {
                var t = $(window).scrollTop();
                e.doMove(t, i)
            })
        },
        doMove: function(e, i) {
            var t, r, a;
            if ($(window).width() < 1160) return void(this.tplMain.is(":visible") && this.tplMain.fadeOut(200));
            if (this.lock && "scroll" == i.type || (this.lock || (this.lock = !0), this.tplMain.css("right", ($(window).width() - 980) / 2 - 90)), t = this, e += 150, r = "", !(e > t.valArray[0])) return void t.tplMain.fadeOut(200);
            for (t.tplMain.css(e - 150 + t.tplMain.outerHeight() + 90 >= t.lastPostion ? {
                position: "absolute",
                top: t.lastPostion - t.tplMain.outerHeight()
            }: {
                position: "fixed",
                top: 90
            }), t.tplMain.is(":hidden") && t.tplMain.fadeIn(200), a = 0; a < t.valArray.length && e >= t.valArray[a]; a++) r = t.idArray[a];
            r != t.nowId && (t.nowId = r, t.tplMain.find("li a").removeClass("select"), $("#" + t.nowId + "-btn").addClass("select"))
        }
    },
    e.prototype.constructor = e,
    new e
}),
$(function() {
    LT.File.Js.load("http://s.lietou-static.com/revs/p/beta2/js/page/slide.guider_fca601d1.js",
    function() { ! LT.User.user_login && 1 != LT.Cookie.get("slide_guide_home") && LT.File.Js.load("http://s.lietou-static.com/revs/p/beta2/js/page/slide.guider_fca601d1.js",
        function() {
            slideGuider({
                second: 3,
                close: function() {
                    LT.Cookie.set("slide_guide_home", "1")
                }
            })
        })
    })
}),
tlog = window.tlog || [],
tlog.push("s:wwwindex_new"),
$(function() {
    $(".candidate .register-box .btn-register").bind("click",
    function() {
        tlog = window.tlog || [],
        tlog.push("c:w_box_register_c_new")
    })
}),
$(".subsite-btn a").bind("mouseenter",
function() {
    var e = $(this).find("span"),
    i = e.attr("class"),
    t = i.replace(/(icons48-[^-]+)/, "$1-white");
    e.attr("class", t)
}).bind("mouseleave",
function() {
    var e = $(this).find("span"),
    i = e.attr("class"),
    t = i.replace("-white", "");
    e.attr("class", t)
}),
$(".search-main-top input").bind("focus",
function() {
    $(this).parent().hasClass("focus") || $(this).parent().addClass("focus")
}).bind("blur",
function() {
    $(this).parent().removeClass("focus")
}),
function() {
    var e, i = "2015-07-02 00:00:00",
    t = "2015-07-04 00:00:00",
    r = LT.Date.format(new Date, "yyyy-MM-dd HH:mm:ss");
    r > i && t > r && (e = '\r\n      <style type="text/css">\r\n      #updateTip{display:none;background:#ffffe5;text-align:center;line-height:25px;color:#e75c00;border-bottom:1px solid #d2d2d2;}\r\n      </style>\r\n      <div id="updateTip">\r\n        <strong>猎聘网将于7月4日凌晨0点至上午7点进行系统升级，期间暂时停止全部服务，由此给您带来不便敬请谅解。</strong>\r\n      </div>\r\n    ', $(e).prependTo("body").show())
} ();