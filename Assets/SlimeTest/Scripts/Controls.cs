using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Controls : MonoBehaviour {

    public Rigidbody2D rb;
    public CircleCollider2D cc2D;
    public SpriteRenderer sr;
    public float movelength;
    public float jumpheight;
    public int hp;
    public bool moveright;
    public bool moveleft;
    public bool jump;
    private Animator anim;

    // Use this for initialization
    void Start () {
        rb = GetComponent<Rigidbody2D>();
        anim = GetComponent<Animator>();
        cc2D = GetComponent<CircleCollider2D>();
        sr = GetComponent<SpriteRenderer>();
    }



    // Update is called once per frame
    void Update () {
        

        if (Input.GetKeyDown(KeyCode.DownArrow))
        {  
            

            StartCoroutine(AutoJump());
          
        }
    }

    void OnCollisionEnter2D(Collision2D col)
    {
        if(col.gameObject.name == "LeftWall"||col.gameObject.name == "RightWall")
        {
            sr.flipX = !sr.flipX;
            movelength = -movelength;
            
        }
        if(col.gameObject.name == "Asteroid")
        {
            hp = hp - 3;
            anim.SetTrigger("slimeDamage");
            if (hp <= 0)
            {
                anim.SetTrigger("slimeDed");
            }
        }
    }

    private IEnumerator AutoJump()
    {
        while (hp > 0)
        {
            rb.velocity = new Vector2(movelength, jumpheight);
            anim.SetTrigger("slimeJump");
            if (hp <= 0)
                yield return null;
            
            yield return new WaitForSeconds(3);
        }
        
        Destroy(gameObject);
    }
}
