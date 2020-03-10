package com.example.myemailrecycler.models

data class Email(
    val user: String,
    val subject: String,
    val preview: String,
    val date: String,
    val stared: Boolean,
    val unread: Boolean,
    var selected: Boolean
)

class EmailBuilder{

    var user: String = ""
    var subject: String= ""
    var preview: String= ""
    var date: String= ""
    var stared: Boolean= false
    var unread: Boolean= false

    fun build(): Email = Email(user, subject, preview, date, stared, unread, false)
}

fun email(block: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(block).build();

fun generateFakeEmails() = mutableListOf<Email>(
    email { user ="Facebook"
        subject ="Veja nossas três dicas principais para você conseguir"
        preview = "Olá Dannylo, você precisa seguir essa dica."
        date= "3 de Março"
        stared= false
        unread = false
    },
    email { user ="Google"
        subject ="Você já acessou o Google Drive hoje?"
        preview = "O Google Drive está com armazenamento ampliado, tudo que você precisa é acessar esse link."
        date= "20 de Fevereiro"
        stared= false
        unread = false
    },
    email { user ="LinkedIn"
        subject ="Seu perfil foi visitado por 5 novas pessoas."
        preview = "Acompanhe o rastreamento de seu perfil com nossa conta Premium."
        date= "31 de Janeiro"
        stared= true
        unread = false
    },
    email { user ="YouTube"
        subject ="Veja os destaques da semana."
        preview = "O mundo todo está acompanhando esses vídeos."
        date= "27 de Janeiro"
        stared= true
        unread = true
    },
    email { user ="Nubank"
        subject ="Sua Fatura está fechada."
        preview = "Acesse o link abaixo para ter acesso ao resumo de sua fatura."
        date= "27 de Janeiro"
        stared= false
        unread = true
    },
    email { user ="Nubank"
        subject ="Sua transferência foi concluída."
        preview = "Sua transferência foi concluída com sucesso em 27 de Janeiro."
        date= "27 de Janeiro"
        stared= false
        unread = true
    },
    email { user ="Google"
        subject ="Você já acessou o Google Drive hoje?"
        preview = "O Google Drive está com armazenamento ampliado, tudo que você precisa é acessar esse link."
        date= "20 de Fevereiro"
        stared= false
        unread = true
    },
    email { user ="LinkedIn"
        subject ="Seu perfil foi visitado por 5 novas pessoas."
        preview = "Acompanhe o rastreamento de seu perfil com nossa conta Premium."
        date= "31 de Janeiro"
        stared= false
        unread = true
    }
)