package models

import anorm._ 
import play.api.db._
import anorm.SqlParser._
import play.api.Play.current

case class Person( id: Long = -1L ) {

    def save() = Person.save( this )

    def delete() = Person.delete( id )

    def exists() = Person.exists( this ) 
}

object Person {

    val simple = {
        get[Long]( "model.id" ) map {  // use ~ get[String]("model.stuff")
            case id => Person( id ) // use case id ~ stuff => Person( id, stuff ) 
        }
    }

    def findAll() = DB.withConnection { implicit connection =>
        SQL( "select * from model" ).as( Person.simple * )
    }

    def findById( id:Long ) = DB.withConnection { implicit connection =>
        SQL( "select * from model s where s.id = {id}" ).on( "id" -> id ).as( Person.simple.singleOpt )
    } 

    def create( model: Person ) = DB.withConnection { implicit connection =>
        val id: Long = Person.nextId()
        /** TODO : update the SQL req below to match your case class structure **/
        SQL( "insert into model values ( {id} )" ).on( "id" -> id ).executeUpdate()
        ( id, Person( id ) )
    }

    def save( model:Person ) = {
        if ( Person.findById( model.id ).isDefined ) {
            Person.update( model.id, model )
        } else {
            Person.create( model )._2
        }
    }

    def delete( id: Long ) = DB.withConnection { implicit connection =>
        SQL( "delete from model where id = {id}" ).on( "id" -> id ).executeUpdate()
    }

    def deleteAll() = DB.withConnection { implicit connection =>
        SQL( "delete from model" ).executeUpdate()
    }

    def update( id: Long, model: Person ) = DB.withConnection { implicit connection =>
        /** TODO : update the SQL req below to match your case class structure **/
        //SQL( "update model set stuff = {stuffvalue} where id = {id}" ).on( "id"-> id, "stuffvalue" -> "value" ).executeUpdate()
        Person( id )
    }

    def count() = DB.withConnection { implicit connection => 
        val firstRow = SQL( "select count(*) as s from model" ).apply().head 
        firstRow[Long]( "s" )
    }

    def nextId() = DB.withConnection { implicit connection =>
        SQL( "select next value for model_seq" ).as( scalar[Long].single )
    }

    def exists( id: Long ) = Person.findById( id ).isDefined
    def exists( model: Person ) = Person.findById( model.id ).isDefined
}